package scala.cli.commands.repl

import caseapp.*
import caseapp.core.help.Help

import scala.cli.commands.shared.{CrossOptions, HelpGroup, SharedJavaOptions, SharedWatchOptions}
import scala.cli.commands.{Constants, tags}

// format: off
final case class SharedReplOptions(
  @Recurse
    sharedJava: SharedJavaOptions = SharedJavaOptions(),
  @Recurse
    watch: SharedWatchOptions = SharedWatchOptions(),
  @Recurse
    compileCross: CrossOptions = CrossOptions(),

  @Group(HelpGroup.Repl.toString)
  @Tag(tags.restricted)
  @Tag(tags.inShortHelp)
  @HelpMessage("Use Ammonite (instead of the default Scala REPL)")
  @Name("A")
  @Name("amm")
    ammonite: Option[Boolean] = None,

  @Group(HelpGroup.Repl.toString)
  @Tag(tags.restricted)
  @HelpMessage(s"Set the Ammonite version (${Constants.ammoniteVersion} by default)")
  @Name("ammoniteVer")
  @Tag(tags.inShortHelp)
    ammoniteVersion: Option[String] = None,

  @Group(HelpGroup.Repl.toString)
  @Name("a")
  @Tag(tags.restricted)
  @Tag(tags.inShortHelp)
  @HelpMessage("Provide arguments for ammonite repl")
  @Hidden
    ammoniteArg: List[String] = Nil,

  @Group(HelpGroup.Repl.toString)
  @Hidden
  @Tag(tags.implementation)
  @HelpMessage("Don't actually run the REPL, just fetch it")
    replDryRun: Boolean = false
)
// format: on

object SharedReplOptions {
  implicit lazy val parser: Parser[SharedReplOptions] = Parser.derive
  implicit lazy val help: Help[SharedReplOptions]     = Help.derive
}
