package scala.cli.integration

import ch.epfl.scala.{bsp4j => b}
import org.eclipse.lsp4j.jsonrpc

import java.io.{InputStream, OutputStream}
import java.util.concurrent.ExecutorService

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.{Failure, Success}

class TestBspClient extends b.BuildClient {
  private val lock                             = new Object
  private val messages0                        = new mutable.ListBuffer[Object]
  private def addMessage(params: Object): Unit =
    lock.synchronized {
      messages0.append(params)
    }

  private val didChangePromises = new mutable.ListBuffer[Promise[b.DidChangeBuildTarget]]
  private val didChangeLock     = new Object
  def buildTargetDidChange(): Future[b.DidChangeBuildTarget] = {
    val p = Promise[b.DidChangeBuildTarget]()
    didChangeLock.synchronized {
      didChangePromises.append(p)
    }
    p.future
  }

  def logMessages(): Seq[b.LogMessageParams] =
    lock.synchronized {
      messages0.reverseIterator.collect {
        case p: b.LogMessageParams => p
      }.toSeq
    }
  def diagnostics(): Seq[b.PublishDiagnosticsParams] =
    lock.synchronized {
      messages0.reverseIterator.collect {
        case p: b.PublishDiagnosticsParams => p
      }.toSeq
    }

  def latestDiagnostics(): Option[b.PublishDiagnosticsParams] =
    lock.synchronized {
      messages0.reverseIterator.collectFirst {
        case p: b.PublishDiagnosticsParams => p
      }
    }

  case class TaskStartFinishDiagnostics(
    startParams: b.TaskStartParams,
    finishParams: b.TaskFinishParams,
    taskMessages: Seq[b.PublishDiagnosticsParams]
  )

  def getTaskDiagnostics(): Seq[TaskStartFinishDiagnostics] =
    lock.synchronized {
      messages0.foldRight(
        Option.empty[TaskStartFinishDiagnostics],
        List.empty[TaskStartFinishDiagnostics]
      ) {
        case (msg: b.TaskFinishParams, (None, acc)) =>
          (Some(TaskStartFinishDiagnostics(null, msg, Nil)), acc)
        case (msg: b.TaskStartParams, (Some(tsfm), acc))
            if tsfm.finishParams.getTaskId == msg.getTaskId =>
          (None, tsfm.copy(startParams = msg) :: acc)
        case (msg: b.PublishDiagnosticsParams, (Some(tsfm), acc)) =>
          (Some(tsfm.copy(taskMessages = msg +: tsfm.taskMessages)), acc)
        case (_, accTuple) => accTuple
      }._2
    }

  def onBuildLogMessage(params: b.LogMessageParams): Unit =
    addMessage(params)
  def onBuildShowMessage(params: b.ShowMessageParams): Unit =
    addMessage(params)
  def onBuildPublishDiagnostics(params: b.PublishDiagnosticsParams): Unit =
    addMessage(params)

  def onBuildTargetDidChange(params: b.DidChangeBuildTarget): Unit = {
    val promises =
      didChangeLock.synchronized {
        val promises0 = didChangePromises.toList
        didChangePromises.clear()
        promises0
      }
    for (p <- promises)
      p.success(params)
    addMessage(params)
  }

  def onBuildTaskStart(params: b.TaskStartParams): Unit =
    addMessage(params)
  def onBuildTaskProgress(params: b.TaskProgressParams): Unit =
    addMessage(params)
  def onBuildTaskFinish(params: b.TaskFinishParams): Unit =
    addMessage(params)
}

object TestBspClient {

  private trait BuildServer extends b.BuildServer with b.ScalaBuildServer with b.JavaBuildServer
      with b.JvmBuildServer

  def connect(
    in: InputStream,
    out: OutputStream,
    es: ExecutorService
  ): (
    TestBspClient,
    b.BuildServer & b.ScalaBuildServer & b.JavaBuildServer & b.JvmBuildServer,
    Future[Unit]
  ) = {

    val localClient = new TestBspClient

    val launcher = new jsonrpc.Launcher.Builder[BuildServer]()
      .setExecutorService(es)
      .setInput(in)
      .setOutput(out)
      .setRemoteInterface(classOf[BuildServer])
      .setLocalService(localClient)
      .create()
    val remoteServer = launcher.getRemoteProxy

    val f  = launcher.startListening()
    val f0 = naiveJavaFutureToScalaFuture(f).map(_ => ())(ExecutionContext.fromExecutor(es))

    (localClient, remoteServer, f0)
  }

  // from https://github.com/com-lihaoyi/Ammonite/blob/7eb58c58ec8c252dc5bd1591b041fcae01cccf90/amm/interp/src/main/scala/ammonite/interp/script/AmmoniteBuildServer.scala#L550-L565
  private def naiveJavaFutureToScalaFuture[T](
    f: java.util.concurrent.Future[T]
  ): Future[T] = {
    val p = Promise[T]()
    val t = new Thread {
      setDaemon(true)
      setName("bsp-wait-for-exit")
      override def run(): Unit =
        p.complete {
          try Success(f.get())
          catch { case t: Throwable => Failure(t) }
        }
    }
    t.start()
    p.future
  }
}
