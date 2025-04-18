package scala.build.preprocessing

import scala.build.EitherCps.{either, value}
import scala.build.Logger
import scala.build.errors.BuildException
import scala.build.internal.markdown.MarkdownCodeBlock
import scala.build.options.SuppressWarningOptions

object MarkdownCodeBlockProcessor {
  def process(
    codeBlocks: Seq[MarkdownCodeBlock],
    reportingPath: Either[String, os.Path],
    scopePath: ScopePath,
    suppressWarningOptions: SuppressWarningOptions,
    logger: Logger,
    maybeRecoverOnError: BuildException => Option[BuildException]
  ): Either[BuildException, PreprocessedMarkdown] = either {
    val (rawCodeBlocks, remaining)         = codeBlocks.partition(_.isRaw)
    val (testCodeBlocks, scriptCodeBlocks) = remaining.partition(_.isTest)
    def preprocessCodeBlocks(cbs: Seq[MarkdownCodeBlock])
      : Either[BuildException, PreprocessedMarkdownCodeBlocks] = either {
      val mergedDirectives: ExtractedDirectives = cbs
        .map { cb =>
          value {
            ExtractedDirectives.from(
              contentChars = cb.body.toCharArray,
              path = reportingPath,
              suppressWarningOptions = suppressWarningOptions,
              logger = logger,
              maybeRecoverOnError = maybeRecoverOnError
            )
          }
        }
        .fold(ExtractedDirectives.empty)(_ ++ _)
      PreprocessedMarkdownCodeBlocks(
        cbs,
        mergedDirectives
      )
    }
    PreprocessedMarkdown(
      value(preprocessCodeBlocks(scriptCodeBlocks)),
      value(preprocessCodeBlocks(rawCodeBlocks)),
      value(preprocessCodeBlocks(testCodeBlocks))
    )
  }
}
