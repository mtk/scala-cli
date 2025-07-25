package scala.cli.errors

import scala.build.errors.BuildException

final class MissingPublishOptionError(
  val name: String,
  val optionName: String,
  val directiveName: String,
  val configKeys: Seq[String] = Nil,
  val extraMessage: String = ""
) extends BuildException(
      {
        val directivePart =
          if (directiveName.isEmpty) ""
          else
            s" or with a '//> using $directiveName' directive"
        val configPart =
          if (configKeys.isEmpty) ""
          else
            s" or by setting ${configKeys.mkString(", ")} in the configuration"
        val extraPart =
          if (extraMessage.isEmpty) "" else s", ${extraMessage.dropWhile(_.isWhitespace)}"

        s"Missing $name for publishing, specify one with $optionName" +
          directivePart +
          configPart +
          extraPart
      }
    )

object MissingPublishOptionError {
  def versionError =
    new MissingPublishOptionError("version", "--project-version", "publish.version")
  def repositoryError =
    new MissingPublishOptionError("repository", "--publish-repository", "publish.repository")
}
