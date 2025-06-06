package scala.build

import ch.epfl.scala.bsp4j as b
import ch.epfl.scala.bsp4j.SourcesItem

import java.util.concurrent.CompletableFuture

import scala.jdk.CollectionConverters.*

package object bsp {

  extension (compileParams: b.CompileParams) {
    def duplicate(): b.CompileParams = {
      val params = new b.CompileParams(compileParams.getTargets)
      params.setOriginId(compileParams.getOriginId)
      params.setArguments(compileParams.getArguments)
      params
    }

    def withExtraArgs(extraArgs: List[String]): b.CompileParams = {
      val params            = compileParams.duplicate()
      val previousArguments = Option(params.getArguments).toList.flatMap(_.asScala)
      val newArguments      = (previousArguments ++ extraArgs).asJava
      params.setArguments(newArguments)
      params
    }

    def withVerbosity(verbose: Boolean): b.CompileParams = {
      val verboseArg                 = "--verbose"
      val argumentsContainVerboseArg =
        Option(compileParams.getArguments).exists(_.contains(verboseArg))
      if verbose && !argumentsContainVerboseArg then compileParams.withExtraArgs(List(verboseArg))
      else compileParams
    }
  }

  implicit class Ext[T](private val f: CompletableFuture[T]) extends AnyVal {
    def logF: CompletableFuture[T] =
      f.handle { (res, err) =>
        if (err == null)
          pprint.err.log(res)
        else
          throw pprint.err.log(err)
      }
  }

  implicit class BuildTargetIdentifierExt(
    private val item: b.BuildTargetIdentifier
  ) extends AnyVal {
    def duplicate(): b.BuildTargetIdentifier =
      new b.BuildTargetIdentifier(item.getUri)
  }

  implicit class SourceItemExt(private val item: b.SourceItem) extends AnyVal {
    def duplicate(): b.SourceItem =
      new b.SourceItem(item.getUri, item.getKind, item.getGenerated)
  }

  implicit class SourcesItemExt(private val item: b.SourcesItem) extends AnyVal {
    def duplicate(): b.SourcesItem = {
      val other = new b.SourcesItem(
        item.getTarget,
        item.getSources.asScala.map(_.duplicate()).asJava
      )
      for (roots <- Option(item.getRoots))
        other.setRoots(roots.asScala.toList.asJava)
      other
    }
  }

  implicit class SourcesResultExt(private val res: b.SourcesResult) extends AnyVal {
    def duplicate(): b.SourcesResult =
      new b.SourcesResult(
        res.getItems().asScala.toList
          .map((item: SourcesItem) => item.duplicate()).asJava
      )
  }

  implicit class BuildTargetCapabilitiesExt(
    private val capabilities: b.BuildTargetCapabilities
  ) extends AnyVal {
    def duplicate(): b.BuildTargetCapabilities =
      val buildTarget = new b.BuildTargetCapabilities()
      buildTarget.setCanCompile(capabilities.getCanCompile)
      buildTarget.setCanTest(capabilities.getCanTest)
      buildTarget.setCanRun(capabilities.getCanRun)
      buildTarget
  }
  implicit class BuildTargetExt(private val target: b.BuildTarget) extends AnyVal {
    def duplicate(): b.BuildTarget = {
      val target0 = new b.BuildTarget(
        target.getId.duplicate(),
        target.getTags.asScala.toList.asJava,
        target.getLanguageIds.asScala.toList.asJava,
        target.getDependencies.asScala.toList.map(_.duplicate()).asJava,
        target.getCapabilities.duplicate()
      )
      target0.setBaseDirectory(target.getBaseDirectory)
      target0.setData(target.getData) // FIXME Duplicate this when we can too?
      target0.setDataKind(target.getDataKind)
      target0.setDisplayName(target.getDisplayName)
      target0
    }
  }
  implicit class WorkspaceBuildTargetsResultExt(
    private val res: b.WorkspaceBuildTargetsResult
  ) extends AnyVal {
    def duplicate(): b.WorkspaceBuildTargetsResult =
      new b.WorkspaceBuildTargetsResult(res.getTargets.asScala.toList.map(_.duplicate()).asJava)
  }

  implicit class LocationExt(private val loc: b.Location) extends AnyVal {
    def duplicate(): b.Location =
      new b.Location(loc.getUri, loc.getRange.duplicate())
  }
  implicit class DiagnosticRelatedInformationExt(
    private val info: b.DiagnosticRelatedInformation
  ) extends AnyVal {
    def duplicate(): b.DiagnosticRelatedInformation =
      new b.DiagnosticRelatedInformation(info.getLocation.duplicate(), info.getMessage)
  }
  implicit class PositionExt(private val pos: b.Position) extends AnyVal {
    def duplicate(): b.Position =
      new b.Position(pos.getLine, pos.getCharacter)
  }
  implicit class RangeExt(private val range: b.Range) extends AnyVal {
    def duplicate(): b.Range =
      new b.Range(range.getStart.duplicate(), range.getEnd.duplicate())
  }
  implicit class DiagnosticExt(private val diag: b.Diagnostic) extends AnyVal {
    def duplicate(): b.Diagnostic = {
      val diag0 = new b.Diagnostic(diag.getRange.duplicate(), diag.getMessage)
      diag0.setCode(diag.getCode)
      diag0.setRelatedInformation(
        Option(diag.getRelatedInformation).map(_.asScala.map(_.duplicate()).asJava).orNull
      )
      diag0.setSeverity(diag.getSeverity)
      diag0.setSource(diag.getSource)
      diag0.setData(diag.getData)
      diag0
    }
  }

}
