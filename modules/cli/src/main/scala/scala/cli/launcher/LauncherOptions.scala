package scala.cli.launcher

import caseapp.*
import com.github.plokhotnyuk.jsoniter_scala.core.JsonValueCodec
import com.github.plokhotnyuk.jsoniter_scala.macros.JsonCodecMaker

import scala.cli.commands.shared.HelpGroup
import scala.cli.commands.tags

@HelpMessage("Run another Scala CLI version")
final case class LauncherOptions(
  @Group(HelpGroup.Launcher.toString)
  @HelpMessage("Set the Scala CLI version")
  @ValueDescription("nightly|version")
  @Tag(tags.implementation)
  @Tag(tags.inShortHelp)
  cliVersion: Option[String] = None,
  @Group(HelpGroup.Launcher.toString)
  @HelpMessage("The version of Scala on which Scala CLI was published")
  @ValueDescription("2.12|2.13|3")
  @Hidden
  @Tag(tags.implementation)
  cliScalaVersion: Option[String] = None,
  @Recurse
  scalaRunner: ScalaRunnerLauncherOptions = ScalaRunnerLauncherOptions(),
  @Recurse
  powerOptions: PowerOptions = PowerOptions()
) {
  def toCliArgs: List[String] =
    cliVersion.toList.flatMap(v => List("--cli-version", v)) ++
      cliScalaVersion.toList.flatMap(v => List("--cli-scala-version", v)) ++
      scalaRunner.toCliArgs ++
      powerOptions.toCliArgs
}

object LauncherOptions {
  implicit lazy val parser: Parser[LauncherOptions]            = Parser.derive
  implicit lazy val help: Help[LauncherOptions]                = Help.derive
  implicit lazy val jsonCodec: JsonValueCodec[LauncherOptions] = JsonCodecMaker.make
}
