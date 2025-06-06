package scala.build.postprocessing

import java.nio.file.{FileAlreadyExistsException, NoSuchFileException}

import scala.build.internal.Constants
import scala.build.options.BuildOptions
import scala.build.tastylib.{TastyData, TastyVersions}
import scala.build.{GeneratedSource, Logger, retry}

case object TastyPostProcessor extends PostProcessor {

  def postProcess(
    generatedSources: Seq[GeneratedSource],
    mappings: Map[String, (String, Int)],
    workspace: os.Path,
    output: os.Path,
    logger: Logger,
    scalaVersion: String,
    buildOptions: BuildOptions
  ): Either[String, Unit] = {

    def updatedPaths = generatedSources
      .flatMap { source =>
        source.reportingPath.toOption.toSeq.map { originalSource =>
          val fromSourceRoot = source.generated.relativeTo(workspace)
          val actual         = originalSource.relativeTo(workspace)
          fromSourceRoot.toString -> actual.toString
        }
      }
      .toMap

    TastyVersions.shouldRunPreprocessor(
      scalaVersion,
      Constants.version,
      buildOptions.scalaOptions.defaultScalaVersion
    ) match {
      case Right(false) => Right(())
      case Left(msg)    => if (updatedPaths.isEmpty) Right(()) else Left(msg)
      case Right(true)  =>
        val paths = updatedPaths
        if (paths.isEmpty) Right(())
        else Right(
          os.walk(output)
            .filter(os.isFile(_))
            .filter(_.last.endsWith(".tasty")) // make that case-insensitive just in case?
            .foreach(updateTastyFile(logger, paths))
        )
    }
  }

  private def updateTastyFile(
    logger: Logger,
    updatedPaths: Map[String, String]
  )(f: os.Path): Unit = {
    logger.debug(s"Reading TASTy file $f")
    try retry()(logger) {
        val content = os.read.bytes(f)
        TastyData.read(content) match {
          case Left(ex)    => logger.debug(s"Ignoring exception during TASty postprocessing: $ex")
          case Right(data) =>
            logger.debug(s"Parsed TASTy file $f")
            var updatedOne  = false
            val updatedData = data.mapNames { n =>
              updatedPaths.get(n) match {
                case Some(newName) =>
                  updatedOne = true
                  newName
                case None =>
                  n
              }
            }
            if updatedOne then {
              logger.debug(
                s"Overwriting ${if f.startsWith(os.pwd) then f.relativeTo(os.pwd) else f}"
              )
              val updatedContent = TastyData.write(updatedData)
              os.write.over(f, updatedContent)
            }
        }
      }
    catch {
      case e: (NoSuchFileException | FileAlreadyExistsException | ArrayIndexOutOfBoundsException) =>
        logger.debugStackTrace(e)
        logger.log(s"Tasty file $f not found: $e. Are you trying to run too many builds at once")
        logger.log("Are you trying to run too many builds at once? Trying to recover...")
    }
  }
}
