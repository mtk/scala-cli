package scala.cli.commands.test

import caseapp.*
import caseapp.core.help.HelpFormat

import java.nio.file.Path

import scala.build.*
import scala.build.EitherCps.{either, value}
import scala.build.Ops.*
import scala.build.errors.{BuildException, CompositeBuildException}
import scala.build.internal.{Constants, Runner}
import scala.build.internals.ConsoleUtils.ScalaCliConsole
import scala.build.options.{BuildOptions, JavaOpt, Platform, Scope}
import scala.build.testrunner.AsmTestRunner
import scala.cli.CurrentParams
import scala.cli.commands.run.Run
import scala.cli.commands.setupide.SetupIde
import scala.cli.commands.shared.{HelpCommandGroup, HelpGroup, SharedOptions}
import scala.cli.commands.update.Update
import scala.cli.commands.{CommandUtils, ScalaCommand, SpecificationLevel, WatchUtil}
import scala.cli.config.Keys
import scala.cli.packaging.Library.fullClassPathMaybeAsJar
import scala.cli.util.ArgHelpers.*
import scala.cli.util.ConfigDbUtils

object Test extends ScalaCommand[TestOptions] {
  override def group: String = HelpCommandGroup.Main.toString
  override def sharedOptions(options: TestOptions): Option[SharedOptions] = Some(options.shared)
  override def scalaSpecificationLevel: SpecificationLevel = SpecificationLevel.SHOULD

  override def helpFormat: HelpFormat =
    super.helpFormat.withPrimaryGroups(Seq(HelpGroup.Test, HelpGroup.Watch))

  private def gray  = ScalaCliConsole.GRAY
  private def reset = Console.RESET

  override def buildOptions(opts: TestOptions): Option[BuildOptions] = Some {
    import opts.*
    val baseOptions = shared.buildOptions().orExit(opts.shared.logger)
    baseOptions.copy(
      javaOptions = baseOptions.javaOptions.copy(
        javaOpts =
          baseOptions.javaOptions.javaOpts ++
            sharedJava.allJavaOpts.map(JavaOpt(_)).map(Positioned.commandLine)
      ),
      testOptions = baseOptions.testOptions.copy(
        frameworks = testFrameworks.map(_.trim).filter(_.nonEmpty).map(Positioned.commandLine),
        testOnly = testOnly.map(_.trim).filter(_.nonEmpty)
      ),
      internalDependencies = baseOptions.internalDependencies.copy(
        addTestRunnerDependencyOpt = Some(true)
      )
    )
  }

  override def runCommand(options: TestOptions, args: RemainingArgs, logger: Logger): Unit = {
    val initialBuildOptions = buildOptionsOrExit(options)
    val inputs              = options.shared.inputs(args.remaining).orExit(logger)
    CurrentParams.workspaceOpt = Some(inputs.workspace)
    SetupIde.runSafe(
      options.shared,
      inputs,
      logger,
      initialBuildOptions,
      Some(name),
      args.remaining
    )
    if (CommandUtils.shouldCheckUpdate)
      Update.checkUpdateSafe(logger)

    val threads = BuildThreads.create()

    val compilerMaker = options.shared.compilerMaker(threads)

    val cross                 = options.compileCross.cross.getOrElse(false)
    val configDb              = ConfigDbUtils.configDb.orExit(logger)
    val actionableDiagnostics =
      options.shared.logging.verbosityOptions.actions.orElse(
        configDb.get(Keys.actions).getOrElse(None)
      )

    /** Runs the tests via [[testOnce]] if build was successful
      * @param builds
      *   build results, checked for failures
      * @param allowExit
      *   false in watchMode
      */
    def maybeTest(builds: Builds, allowExit: Boolean): Unit =
      if (builds.anyFailed) {
        System.err.println("Compilation failed")
        if (allowExit)
          sys.exit(1)
      }
      else {
        val optionsKeys = builds.map.keys.toVector.map(_.optionsKey).distinct
        val builds0     = optionsKeys.flatMap { optionsKey =>
          builds.map.get(CrossKey(optionsKey, Scope.Test))
        }
        val buildsLen                = builds0.length
        val printBeforeAfterMessages =
          buildsLen > 1 && options.shared.logging.verbosity >= 0
        val results =
          for ((s, idx) <- builds0.zipWithIndex) yield {
            if (printBeforeAfterMessages) {
              val scalaStr    = s.crossKey.scalaVersion.versionOpt.fold("")(v => s" for Scala $v")
              val platformStr = s.crossKey.platform.fold("")(p => s", ${p.repr}")
              System.err.println(
                s"${gray}Running tests$scalaStr$platformStr$reset"
              )
              System.err.println()
            }
            val retCodeOrError = testOnce(
              s,
              options.requireTests,
              args.unparsed,
              logger,
              allowExecve = allowExit && buildsLen <= 1,
              asJar = options.shared.asJar
            )
            if (printBeforeAfterMessages && idx < buildsLen - 1)
              System.err.println()
            retCodeOrError
          }

        val maybeRetCodes = results.sequence
          .left.map(CompositeBuildException(_))

        val retCodesOpt =
          if (allowExit)
            Some(maybeRetCodes.orExit(logger))
          else
            maybeRetCodes.orReport(logger)

        for (retCodes <- retCodesOpt if !retCodes.forall(_ == 0))
          if (allowExit)
            sys.exit(retCodes.find(_ != 0).getOrElse(1))
          else {
            val red      = Console.RED
            val lightRed = "\u001b[91m"
            val reset    = Console.RESET
            System.err.println(
              s"${red}Tests exited with return code $lightRed${retCodes.mkString(", ")}$red.$reset"
            )
          }
      }

    if (options.watch.watchMode) {
      val watcher = Build.watch(
        inputs,
        initialBuildOptions,
        compilerMaker,
        None,
        logger,
        crossBuilds = cross,
        buildTests = true,
        partial = None,
        actionableDiagnostics = actionableDiagnostics,
        postAction = () => WatchUtil.printWatchMessage()
      ) { res =>
        for (builds <- res.orReport(logger))
          maybeTest(builds, allowExit = false)
      }
      try WatchUtil.waitForCtrlC(() => watcher.schedule())
      finally watcher.dispose()
    }
    else {
      val builds =
        Build.build(
          inputs,
          initialBuildOptions,
          compilerMaker,
          None,
          logger,
          crossBuilds = cross,
          buildTests = true,
          partial = None,
          actionableDiagnostics = actionableDiagnostics
        )
          .orExit(logger)
      maybeTest(builds, allowExit = true)
    }
  }

  private def testOnce(
    build: Build.Successful,
    requireTests: Boolean,
    args: Seq[String],
    logger: Logger,
    asJar: Boolean,
    allowExecve: Boolean
  ): Either[BuildException, Int] = either {

    val predefinedTestFrameworks = build.options.testOptions.frameworks

    build.options.platform.value match {
      case Platform.JS =>
        val linkerConfig = build.options.scalaJsOptions.linkerConfig(logger)
        val esModule     =
          build.options.scalaJsOptions.moduleKindStr.exists(m => m == "es" || m == "esmodule")
        value {
          Run.withLinkedJs(
            Seq(build),
            None,
            addTestInitializer = true,
            linkerConfig,
            value(build.options.scalaJsOptions.fullOpt),
            build.options.scalaJsOptions.noOpt.getOrElse(false),
            logger,
            esModule
          ) { js =>
            Runner.testJs(
              build.fullClassPath.map(_.toNIO),
              js.toIO,
              requireTests,
              args,
              predefinedTestFrameworks.map(_.value),
              logger,
              build.options.scalaJsOptions.dom.getOrElse(false),
              esModule
            )
          }.flatten
        }
      case Platform.Native =>
        value {
          Run.withNativeLauncher(
            Seq(build),
            "scala.scalanative.testinterface.TestMain",
            logger
          ) { launcher =>
            Runner.testNative(
              build.fullClassPath.map(_.toNIO),
              launcher.toIO,
              predefinedTestFrameworks.map(_.value),
              requireTests,
              args,
              logger
            )
          }.flatten
        }
      case Platform.JVM =>
        val classPath = build.fullClassPathMaybeAsJar(asJar)

        val predefinedTestFrameworks0 =
          predefinedTestFrameworks match {
            case f if f.nonEmpty => f
            case Nil             =>
              findTestFramework(classPath.map(_.toNIO), logger).map(Positioned.none).toList
          }
        val testOnly = build.options.testOptions.testOnly

        val extraArgs =
          (if requireTests then Seq("--require-tests") else Nil) ++
            build.options.internal.verbosity.map(v => s"--verbosity=$v") ++
            predefinedTestFrameworks0.map(_.value).map(fw => s"--test-framework=$fw") ++
            testOnly.map(to => s"--test-only=$to").toSeq ++
            Seq("--") ++ args

        Runner.runJvm(
          build.options.javaHome().value.javaCommand,
          build.options.javaOptions.javaOpts.toSeq.map(_.value.value),
          classPath,
          Constants.testRunnerMainClass,
          extraArgs,
          logger,
          allowExecve = allowExecve
        ).waitFor()
    }
  }

  private def findTestFramework(classPath: Seq[Path], logger: Logger): Option[String] = {
    val classPath0 = classPath.map(_.toString)

    // https://github.com/VirtusLab/scala-cli/issues/426
    if classPath0.exists(_.contains("zio-test")) && !classPath0.exists(_.contains("zio-test-sbt"))
    then {
      val parentInspector = new AsmTestRunner.ParentInspector(classPath)
      Runner.frameworkNames(classPath, parentInspector, logger) match {
        case Right(f) => f.headOption
        case Left(_)  =>
          logger.message(
            s"zio-test found in the class path, zio-test-sbt should be added to run zio tests with $fullRunnerName."
          )
          None
      }
    }
    else None
  }

}
