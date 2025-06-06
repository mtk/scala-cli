package scala.cli.exportCmd

import coursier.ivy.IvyRepository
import coursier.maven.MavenRepository
import coursier.parse.RepositoryParser
import dependency.{AnyDependency, NoAttributes, ScalaNameAttributes}

import java.nio.file.Path

import scala.build.errors.BuildException
import scala.build.internal.Runner.frameworkNames
import scala.build.options.{
  BuildOptions,
  Platform,
  ScalaJsOptions,
  ScalaNativeOptions,
  Scope,
  ShadowingSeq
}
import scala.build.testrunner.AsmTestRunner
import scala.build.{Logger, Positioned, Sources}
import scala.cli.ScalaCli

final case class SbtProjectDescriptor(
  sbtVersion: String,
  extraSettings: Seq[String],
  logger: Logger
) extends ProjectDescriptor {
  private val q  = "\""
  private val nl = System.lineSeparator()

  private def sources(sourcesMain: Sources, sourcesTest: Sources): SbtProject = {
    val mainSources = ProjectDescriptor.sources(sourcesMain)
    val testSources = ProjectDescriptor.sources(sourcesTest)
    SbtProject(
      mainSources = mainSources,
      testSources = testSources
    )
  }

  private def sbtVersionProject: SbtProject =
    SbtProject(sbtVersion = Some(sbtVersion))

  private def pureJavaSettings(options: BuildOptions, sources: Sources): SbtProject = {

    val pureJava = ProjectDescriptor.isPureJavaProject(options, sources)

    val settings =
      if (pureJava)
        Seq(
          "crossPaths := false",
          "autoScalaLibrary := false"
        )
      else if (options.scalaOptions.addScalaLibrary.getOrElse(true))
        Nil
      else
        Seq(
          "autoScalaLibrary := false"
        )

    SbtProject(settings = Seq(settings))
  }

  private def scalaJsSettings(options: ScalaJsOptions): SbtProject = {

    val plugins = Seq(
      s""""org.scala-js" % "sbt-scalajs" % "${options.finalVersion}""""
    )
    val pluginSettings = Seq(
      "enablePlugins(ScalaJSPlugin)",
      "scalaJSUseMainModuleInitializer := true"
    )

    val linkerConfigCalls    = ProjectDescriptor.scalaJsLinkerCalls(options, logger)
    val linkerConfigSettings =
      if (linkerConfigCalls.isEmpty) Nil
      else
        Seq(s"""scalaJSLinkerConfig ~= { _${linkerConfigCalls.mkString} }""")

    // TODO options.dom

    SbtProject(
      plugins = plugins,
      settings = Seq(pluginSettings, linkerConfigSettings)
    )
  }

  private def scalaNativeSettings(options: ScalaNativeOptions): SbtProject = {

    val plugins = Seq(
      s""""org.scala-native" % "sbt-scala-native" % "${options.finalVersion}""""
    )
    val pluginSettings = Seq(
      "enablePlugins(ScalaNativePlugin)"
    )

    val configCalls = Seq.empty[String]

    val (configImports, configSettings) =
      if (configCalls.isEmpty) ("", Nil)
      else
        (
          "import scala.scalanative.build._",
          Seq(s"""nativeConfig ~= { _${configCalls.mkString} }""")
        )

    SbtProject(
      plugins = plugins,
      settings = Seq(pluginSettings, configSettings),
      imports = Seq(configImports)
    )
  }

  private def scalaVersionSettings(options: BuildOptions): SbtProject = {

    val scalaVerSetting = {

      val sv = options.scalaParams.toOption.flatten.map(_.scalaVersion).getOrElse(
        ScalaCli.getDefaultScalaVersion
      )

      s"""scalaVersion := "$sv""""
    }

    SbtProject(
      settings = Seq(Seq(scalaVerSetting))
    )
  }

  private def repositorySettings(options: BuildOptions): SbtProject = {

    val repoSettings =
      if (options.classPathOptions.extraRepositories.isEmpty) Nil
      else {
        val repos = options.classPathOptions
          .extraRepositories
          .map(repo => (repo, RepositoryParser.repository(repo)))
          .zipWithIndex
          .map {
            case ((_, Right(repo: IvyRepository)), idx) =>
              // TODO repo.authentication?
              // TODO repo.metadataPatternOpt
              s"""Resolver.url("repo-$idx") artifacts "${repo.pattern.string}""""
            case ((_, Right(repo: MavenRepository)), idx) =>
              // TODO repo.authentication?
              s""""repo-$idx" at "${repo.root}""""
            case _ =>
              ???
          }
        Seq(s"""resolvers ++= Seq(${repos.mkString(", ")})""")
      }

    SbtProject(
      settings = Seq(repoSettings)
    )
  }

  private def customResourcesSettings(options: BuildOptions): SbtProject = {
    val customResourceSettings =
      if (options.classPathOptions.resourcesDir.isEmpty) Nil
      else {
        val resources = options.classPathOptions.resourcesDir.map(p => s"""file("$p")""")
        Seq(
          s"""Compile / unmanagedResourceDirectories ++= Seq(${resources.mkString(", ")})"""
        )
      }

    SbtProject(
      settings = Seq(customResourceSettings)
    )
  }

  private def customJarsSettings(options: BuildOptions): SbtProject = {

    val customCompileOnlyJarsSettings =
      if (options.classPathOptions.extraCompileOnlyJars.isEmpty) Nil
      else {
        val jars = options.classPathOptions.extraCompileOnlyJars.map(p => s"""file("$p")""")
        Seq(s"""Compile / unmanagedClasspath ++= Seq(${jars.mkString(", ")})""")
      }

    val customJarsSettings =
      if (options.classPathOptions.extraClassPath.isEmpty) Nil
      else {
        val jars = options.classPathOptions.extraClassPath.map(p => s"""file("$p")""")
        Seq(
          s"""Compile / unmanagedClasspath ++= Seq(${jars.mkString(", ")})""",
          s"""Runtime / unmanagedClasspath ++= Seq(${jars.mkString(", ")})"""
        )
      }

    SbtProject(
      settings = Seq(customCompileOnlyJarsSettings, customJarsSettings)
    )
  }

  private def javaOptionsSettings(options: BuildOptions): SbtProject = {

    val javaOptionsSettings =
      if (options.javaOptions.javaOpts.toSeq.isEmpty) Nil
      else
        Seq(
          "run / javaOptions ++= Seq(" + nl +
            options.javaOptions
              .javaOpts
              .toSeq
              .map(_.value.value)
              .map { opt =>
                "  \"" + opt + "\"," + nl
              }
              .mkString +
            ")"
        )

    SbtProject(
      settings = Seq(javaOptionsSettings)
    )
  }

  private def mainClassSettings(options: BuildOptions): SbtProject = {

    val mainClassOptions = options.mainClass match {
      case None            => Nil
      case Some(mainClass) =>
        Seq(s"""Compile / mainClass := Some("$mainClass")""")
    }

    SbtProject(
      settings = Seq(mainClassOptions)
    )
  }

  private def scalacOptionsSettings(options: BuildOptions): SbtProject = {

    val scalacOptionsSettings =
      if (options.scalaOptions.scalacOptions.toSeq.isEmpty) Nil
      else {
        val options0 = options
          .scalaOptions
          .scalacOptions
          .toSeq
          .map(_.value.value)
          .map(o => "\"" + o.replace("\"", "\\\"") + "\"")
        Seq(s"""scalacOptions ++= Seq(${options0.mkString(", ")})""")
      }

    SbtProject(
      settings = Seq(scalacOptionsSettings)
    )
  }

  private def testFrameworkSettings(options: BuildOptions): SbtProject = {

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

    val testFrameworkSettings = frameworkName0 match {
      case None     => Nil
      case Some(fw) =>
        Seq(s"""testFrameworks += new TestFramework("$fw")""")
    }

    SbtProject(
      settings = Seq(testFrameworkSettings)
    )
  }

  private def dependencySettings(options: BuildOptions, scope: Scope): SbtProject = {

    val depSettings = {
      def toDepString(deps: ShadowingSeq[Positioned[AnyDependency]], isCompileOnly: Boolean) =
        deps.toSeq.toList.map(_.value).map { dep =>
          val org  = dep.organization
          val name = dep.name
          val ver  = dep.version
          // TODO dep.userParams
          // TODO dep.exclude
          // TODO dep.attributes
          val (sep, suffixOpt) = dep.nameAttributes match {
            case NoAttributes           => ("%", None)
            case s: ScalaNameAttributes =>
              val suffixOpt0 =
                if (s.fullCrossVersion.getOrElse(false)) Some(".cross(CrossVersion.full)")
                else None
              val sep = "%%"
              (sep, suffixOpt0)
          }
          val scope0 =
            // FIXME This ignores the isCompileOnly when scope == Scope.Test
            if (scope == Scope.Test) "% Test"
            else if (isCompileOnly) "% Provided"
            else ""

          val baseDep = s"""$q$org$q $sep $q$name$q % $q$ver$q $scope0"""
          suffixOpt.fold(baseDep)(suffix => s"($baseDep)$suffix")
        }

      val allDepStrings = toDepString(options.classPathOptions.extraDependencies, false) ++
        toDepString(options.classPathOptions.extraCompileOnlyDependencies, true)

      if (allDepStrings.isEmpty) Nil
      else if (allDepStrings.lengthCompare(1) == 0)
        Seq(s"""libraryDependencies += ${allDepStrings.head}""")
      else {
        val count   = allDepStrings.length
        val allDeps = allDepStrings
          .iterator
          .zipWithIndex
          .map {
            case (dep, idx) =>
              val maybeComma = if (idx == count - 1) "" else ","
              "  " + dep + maybeComma + nl
          }
          .mkString
        Seq(s"""libraryDependencies ++= Seq($nl$allDeps)""")
      }
    }

    SbtProject(
      settings = Seq(depSettings)
    )
  }

  def `export`(
    optionsMain: BuildOptions,
    optionsTest: BuildOptions,
    sourcesMain: Sources,
    sourcesTest: Sources
  ): Either[BuildException, SbtProject] = {

    // TODO Handle Scala CLI cross-builds

    val projectChunks = Seq(
      SbtProject(settings = Seq(extraSettings)),
      sources(sourcesMain, sourcesTest),
      sbtVersionProject,
      scalaVersionSettings(optionsMain),
      scalacOptionsSettings(optionsMain),
      mainClassSettings(optionsMain),
      pureJavaSettings(optionsMain, sourcesMain),
      javaOptionsSettings(optionsMain),
      if (optionsMain.platform.value == Platform.JS)
        scalaJsSettings(optionsMain.scalaJsOptions)
      else
        SbtProject(),
      if (optionsMain.platform.value == Platform.Native)
        scalaNativeSettings(optionsMain.scalaNativeOptions)
      else
        SbtProject(),
      customJarsSettings(optionsMain),
      customResourcesSettings(optionsMain),
      testFrameworkSettings(optionsTest),
      repositorySettings(optionsMain),
      dependencySettings(optionsMain, Scope.Main),
      dependencySettings(optionsTest, Scope.Test)
    )

    Right(projectChunks.foldLeft(SbtProject())(_ + _))
  }
}
