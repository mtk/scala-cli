package scala.build.preprocessing.directives

import scala.build.directives.*
import scala.build.errors.BuildException
import scala.build.options.BuildOptions
import scala.build.{Positioned, options}
import scala.cli.commands.SpecificationLevel

@DirectiveGroupName("Main class")
@DirectiveExamples("//> using mainClass HelloWorld")
@DirectiveUsage(
  "//> using mainClass _main-class_",
  "`//> using mainClass` _main-class_"
)
@DirectiveDescription("Specify default main class")
@DirectiveLevel(SpecificationLevel.MUST)
// format: off
final case class MainClass(
  mainClass: Option[String] = None
) extends HasBuildOptions {
  // format: on
  def buildOptions: Either[BuildException, BuildOptions] = {
    val buildOpt = BuildOptions(
      mainClass = mainClass
    )
    Right(buildOpt)
  }
}

object MainClass {
  val handler: DirectiveHandler[MainClass] = DirectiveHandler.derive
}
