package scala.cli.exportCmd

import coursier.maven.MavenRepository
import coursier.parse.RepositoryParser

import java.nio.file.Path

import scala.build.errors.BuildException
import scala.build.internal.Constants
import scala.build.internal.Runner.frameworkNames
import scala.build.options.{BuildOptions, Platform, ScalaJsOptions, ScalaNativeOptions, Scope}
import scala.build.testrunner.AsmTestRunner
import scala.build.{Logger, Sources}
import scala.cli.ScalaCli

final case class MillProjectDescriptor(
  millVersion: String,
  projectName: Option[String] = None,
  launchers: Seq[(os.RelPath, Array[Byte])],
  logger: Logger
) extends ProjectDescriptor {

  private def sourcesSettings(mainSources: Sources, testSources: Sources): MillProject = {
    val mainSources0 = ProjectDescriptor.sources(mainSources)
    val testSources0 = ProjectDescriptor.sources(testSources)
    MillProject(mainSources = mainSources0, testSources = testSources0)
  }

  private def scalaVersionSettings(options: BuildOptions, sources: Sources): MillProject = {

    val pureJava = ProjectDescriptor.isPureJavaProject(options, sources)

    val sv = options.scalaParams.toOption.flatten.map(_.scalaVersion).getOrElse(
      ScalaCli.getDefaultScalaVersion
    )

    if (pureJava)
      MillProject()
    else
      MillProject(scalaVersion = Some(sv))
  }

  private def scalaCompilerPlugins(buildOptions: BuildOptions): MillProject =
    MillProject(scalaCompilerPlugins =
      buildOptions.scalaOptions.compilerPlugins.toSeq.map(_.value.render)
    )

  private def scalacOptionsSettings(buildOptions: BuildOptions): MillProject =
    MillProject(scalacOptions = buildOptions.scalaOptions.scalacOptions.toSeq.map(_.value.value))

  private def scalaJsSettings(options: ScalaJsOptions): MillProject = {

    val scalaJsVersion = Some(options.version.getOrElse(Constants.scalaJsVersion))

    val moduleKindDecls =
      if (options.moduleKindStr.isEmpty) Nil
      else
        Seq(s"""def moduleKind = ModuleKind.${options.moduleKind(logger)}""")

    MillProject(
      scalaJsVersion = scalaJsVersion,
      extraDecls = moduleKindDecls
    )
  }

  private def scalaNativeSettings(options: ScalaNativeOptions): MillProject =
    MillProject(scalaNativeVersion = Some(options.finalVersion))

  private def dependencySettings(
    mainOptions: BuildOptions,
    testOptions: BuildOptions
  ): MillProject = {
    val mainDeps        = mainOptions.classPathOptions.extraDependencies.toSeq.map(_.value.render)
    val compileMainDeps =
      mainOptions.classPathOptions.extraCompileOnlyDependencies.toSeq.map(_.value.render)
    val testDeps        = testOptions.classPathOptions.extraDependencies.toSeq.map(_.value.render)
    val compileTestDeps =
      testOptions.classPathOptions.extraCompileOnlyDependencies.toSeq.map(_.value.render)
    MillProject(
      mainDeps = mainDeps.toSeq,
      mainCompileOnlyDeps = compileMainDeps.toSeq,
      testDeps = testDeps.toSeq,
      testCompileOnlyDeps = compileTestDeps.toSeq
    )
  }

  private def repositorySettings(options: BuildOptions): MillProject = {

    val repoDecls =
      if (options.classPathOptions.extraRepositories.isEmpty) Nil
      else {
        val repos = options.classPathOptions
          .extraRepositories
          .map(repo => RepositoryParser.repository(repo))
          .map {
            case Right(repo: MavenRepository) =>
              // TODO repo.authentication?
              s"""coursier.maven.MavenRepository("${repo.root}")"""
            case _ =>
              ???
          }
        Seq(s"""def repositories = super.repositories ++ Seq(${repos.mkString(", ")})""")
      }

    MillProject(
      extraDecls = repoDecls
    )
  }

  private def customResourcesSettings(options: BuildOptions): MillProject =
    MillProject(
      resourcesDirs = options.classPathOptions.resourcesDir
    )

  private def customJarsSettings(options: BuildOptions): MillProject = {

    val customCompileOnlyJarsDecls =
      if (options.classPathOptions.extraCompileOnlyJars.isEmpty) Nil
      else {
        val jars =
          options.classPathOptions.extraCompileOnlyJars.map(p => s"""PathRef(os.Path("$p"))""")
        Seq(s"""def compileClasspath = super.compileClasspath() ++ Seq(${jars.mkString(", ")})""")
      }

    val customJarsDecls =
      if (options.classPathOptions.extraClassPath.isEmpty) Nil
      else {
        val jars = options.classPathOptions.extraClassPath.map(p => s"""PathRef(os.Path("$p"))""")
        Seq(
          s"""def unmanagedClasspath = super.unmanagedClasspath() ++ Seq(${jars.mkString(", ")})"""
        )
      }

    MillProject(
      extraDecls = customCompileOnlyJarsDecls ++ customJarsDecls
    )
  }

  private def testFrameworkSettings(options: BuildOptions): MillProject = {

    val testClassPath: Seq[Path] = options.artifacts(logger, Scope.Test) match {
      case Right(artifacts) => artifacts.classPath.map(_.toNIO)
      case Left(exception)  =>
        logger.debug(exception.message)
        Seq.empty
    }
    val parentInspector = new AsmTestRunner.ParentInspector(testClassPath)
    val frameworkName0  = options.testOptions.frameworks.headOption.orElse {
      frameworkNames(testClassPath, parentInspector, logger).toOption
        .flatMap(_.headOption) // TODO: handle multiple frameworks here
    }

    val testFrameworkDecls = frameworkName0 match {
      case None     => Nil
      case Some(fw) =>
        Seq(s"""def testFramework = "$fw"""")
    }

    MillProject(
      extraTestDecls = testFrameworkDecls
    )
  }

  def `export`(
    optionsMain: BuildOptions,
    optionsTest: BuildOptions,
    sourcesMain: Sources,
    sourcesTest: Sources
  ): Either[BuildException, MillProject] = {

    // FIXME Put a sensible value in MillProject.nameOpt

    val baseSettings = MillProject(
      millVersion = Some(millVersion),
      nameOpt = projectName,
      launchers = launchers,
      mainClass = optionsMain.mainClass
    )

    val settings = Seq(
      baseSettings,
      sourcesSettings(sourcesMain, sourcesTest),
      scalaVersionSettings(optionsMain, sourcesMain),
      scalacOptionsSettings(optionsMain),
      scalaCompilerPlugins(optionsMain),
      dependencySettings(optionsMain, optionsTest),
      repositorySettings(optionsMain),
      if (optionsMain.platform.value == Platform.JS) scalaJsSettings(optionsMain.scalaJsOptions)
      else MillProject(),
      if (optionsMain.platform.value == Platform.Native)
        scalaNativeSettings(optionsMain.scalaNativeOptions)
      else MillProject(),
      customResourcesSettings(optionsMain),
      customJarsSettings(optionsMain),
      testFrameworkSettings(optionsTest)
    )

    Right(settings.foldLeft(MillProject())(_ + _))
  }
}
