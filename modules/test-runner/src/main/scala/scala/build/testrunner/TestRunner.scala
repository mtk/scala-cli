package scala.build.testrunner

import sbt.testing.{Logger => SbtTestLogger, _}

import java.io.{File, PrintStream}
import java.nio.file.{Path, Paths}

import scala.collection.mutable

object TestRunner {

  def commonTestFrameworks: Seq[String] = Seq(
    "munit.Framework",
    "utest.runner.Framework",
    "org.scalacheck.ScalaCheckFramework",
    "zio.test.sbt.ZTestFramework",
    "org.scalatest.tools.Framework",
    "com.novocode.junit.JUnitFramework",
    "org.scalajs.junit.JUnitFramework",
    "weaver.framework.CatsEffect"
  )

  def classPath(loader: ClassLoader): Seq[Path] = {
    def helper(loader: ClassLoader): Stream[Path] =
      if (loader == null) Stream.empty
      else {
        val paths = loader match {
          case u: java.net.URLClassLoader =>
            u.getURLs
              .flatMap {
                case url if url.getProtocol == "file" =>
                  Seq(Paths.get(url.toURI).toAbsolutePath)
                case _ => Nil // FIXME Warn about this
              }
              .toStream
          case cl if cl.getClass.getName == "jdk.internal.loader.ClassLoaders$AppClassLoader" =>
            // Required with JDK-11
            sys.props.getOrElse("java.class.path", "")
              .split(File.pathSeparator)
              .toStream
              .map(Paths.get(_))
          case _ => Stream.empty // FIXME Warn about this
        }
        paths #::: helper(loader.getParent)
      }
    helper(loader).toVector
  }

  // initially based on https://github.com/com-lihaoyi/mill/blob/e4c838cf9347ec3659d487af2121c9960d5842e8/scalalib/src/TestRunner.scala#L218-L248
  def runTasks(initialTasks: Seq[Task], out: PrintStream): Seq[Event] = {

    val tasks = new mutable.Queue[Task]
    tasks ++= initialTasks

    val events = mutable.Buffer.empty[Event]

    val logger: SbtTestLogger =
      new SbtTestLogger {
        def error(msg: String): Unit      = out.println(msg)
        def warn(msg: String): Unit       = out.println(msg)
        def info(msg: String): Unit       = out.println(msg)
        def debug(msg: String): Unit      = out.println(msg)
        def trace(t: Throwable): Unit     = t.printStackTrace(out)
        def ansiCodesSupported(): Boolean = true
      }

    val eventHandler: EventHandler = (event: Event) => events.append(event)

    while (tasks.nonEmpty) {
      val task     = tasks.dequeue()
      val newTasks = task.execute(eventHandler, Array(logger))
      tasks ++= newTasks
    }

    events.toVector
  }

}
