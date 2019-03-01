import scala.util.Properties.envOrElse
import play.sbt.PlayImport._
import play.core.PlayVersion
import sbt.Tests.{SubProcess, Group}
import play.routes.compiler.StaticRoutesGenerator
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin._
import uk.gov.hmrc._
import DefaultBuildSettings._
import uk.gov.hmrc.SbtAutoBuildPlugin
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin
import uk.gov.hmrc.versioning.SbtGitVersioning
import _root_.play.sbt.routes.RoutesKeys.routesGenerator
import uk.gov.hmrc.SbtArtifactory

lazy val appName = "marriage-allowance-des-stub"
lazy val appDependencies: Seq[ModuleID] = compile ++ test ++ itTest

val hmrcTestVersion = "3.5.0-play-25"
val scalaTestPlusPlayVersion = "2.0.1"

lazy val compile: Seq[ModuleID] = Seq(
  ws,
  "uk.gov.hmrc" %% "microservice-bootstrap" % "10.4.0",
  "uk.gov.hmrc" %% "domain" % "5.3.0",
  "uk.gov.hmrc" %% "play-reactivemongo" % "6.4.0",
  "uk.gov.hmrc" %% "play-hmrc-api" % "3.4.0-play-25"
)

lazy val test: Seq[ModuleID] = Seq(
  "uk.gov.hmrc" %% "hmrctest" % hmrcTestVersion,
  "uk.gov.hmrc" %% "reactivemongo-test" % "3.1.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestPlusPlayVersion,
  "org.mockito" % "mockito-core" % "2.24.5"
).map(_ % "test")

val itTest: Seq[ModuleID] = Seq(
  "uk.gov.hmrc" %% "hmrctest" % hmrcTestVersion,
  "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestPlusPlayVersion,
  "org.scalaj" %% "scalaj-http" % "2.4.1",
  "com.github.tomakehurst" % "wiremock" % "2.21.0"
).map(_ % "it")

lazy val plugins : Seq[Plugins] = Seq(play.sbt.PlayScala, SbtAutoBuildPlugin, SbtGitVersioning, SbtDistributablesPlugin, SbtArtifactory)

lazy val microservice = (project in file("."))
  .enablePlugins(Seq(_root_.play.sbt.PlayScala, SbtAutoBuildPlugin, SbtGitVersioning, SbtDistributablesPlugin) ++ plugins: _*)
  .settings(scalaSettings: _*)
  .settings(majorVersion := 0)
  .settings(publishingSettings: _*)
  .settings(defaultSettings(): _*)
  .settings(routesImport += "controllers.Binders._")
  .settings(unmanagedResourceDirectories in Compile += baseDirectory.value / "resources")
  .settings(
    name := appName,
    libraryDependencies ++= appDependencies,
    retrieveManaged := true,
    evictionWarningOptions in update := EvictionWarningOptions.default.withWarnScalaVersionEviction(false),
    routesGenerator := StaticRoutesGenerator
  )
  .configs(IntegrationTest)
  .settings(
    integrationTestSettings(): _*
  )
  .settings(resolvers ++= Seq(
    Resolver.bintrayRepo("hmrc", "releases"),
    Resolver.jcenterRepo
  ))

// Coverage configuration
coverageMinimum := 75
coverageFailOnMinimum := true
coverageExcludedPackages := "<empty>;com.kenshoo.play.metrics.*;.*definition.*;prod.*;testOnlyDoNotUseInAppConf.*;app.*;uk.gov.hmrc.BuildInfo"

