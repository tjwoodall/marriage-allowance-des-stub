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

lazy val microserviceBootstrapVersion = "5.13.0"
lazy val playConfigVersion = "4.2.0"
lazy val playHealthVersion = "2.1.0"
lazy val playUiVersion = "7.2.1"
lazy val logbackJsonLoggerVersion = "3.1.0"
lazy val domainVersion = "4.1.0"
lazy val playReactiveMongoVersion = "5.2.0"
lazy val scalaJVersion = "1.1.6"
lazy val playHmrcApiVersion = "1.4.0"
lazy val playAuthVersion = "4.3.0"
lazy val hmrcTestVersion = "2.3.0"
lazy val hmrcReactiveMongoTestVersion = "2.0.0"
lazy val scalaTestVersion = "2.2.6"
lazy val scalaTestPlusPlayVersion = "1.5.1"
lazy val mockitoVersion = "2.8.9"
lazy val pegdownVersion = "1.6.0"
lazy val wiremockVersion = "1.58"

lazy val appName = "marriage-allowance-des-stub"
lazy val appVersion = envOrElse("MARRIAGE_ALLOWANCE_DES_STUB_VERSION", "999-SNAPSHOT")

lazy val appDependencies: Seq[ModuleID] = compile ++ test

lazy val compile = Seq(
  ws,
  "uk.gov.hmrc" %% "microservice-bootstrap" % microserviceBootstrapVersion,
  "uk.gov.hmrc" %% "play-config" % playConfigVersion,
  "uk.gov.hmrc" %% "play-health" % playHealthVersion,
  "uk.gov.hmrc" %% "play-ui" % playUiVersion,
  "uk.gov.hmrc" %% "logback-json-logger" % logbackJsonLoggerVersion,
  "uk.gov.hmrc" %% "domain" % domainVersion,
  "uk.gov.hmrc" %% "play-reactivemongo" % playReactiveMongoVersion,
  "uk.gov.hmrc" %% "play-hmrc-api" % playHmrcApiVersion,
  "uk.gov.hmrc" %% "play-authorisation" % playAuthVersion
)

lazy val scope: String = "test, it"

lazy val test = Seq(
  "uk.gov.hmrc" %% "hmrctest" % hmrcTestVersion % scope,
  "uk.gov.hmrc" %% "reactivemongo-test" % hmrcReactiveMongoTestVersion % scope,
  "org.scalatest" %% "scalatest" % scalaTestVersion % scope,
  "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestPlusPlayVersion % scope,
  "org.mockito" % "mockito-core" % mockitoVersion % scope,
  "org.pegdown" % "pegdown" % pegdownVersion % scope,
  "com.typesafe.play" %% "play-test" % PlayVersion.current % scope,
  "org.scalaj" %% "scalaj-http" % scalaJVersion % scope,
  "com.github.tomakehurst" % "wiremock" % wiremockVersion % scope
  )

lazy val plugins: Seq[Plugins] = Seq.empty
lazy val playSettings: Seq[Setting[_]] = Seq.empty

lazy val microservice = (project in file("."))
  .enablePlugins(Seq(_root_.play.sbt.PlayScala, SbtAutoBuildPlugin, SbtGitVersioning, SbtDistributablesPlugin) ++ plugins: _*)
  .settings(playSettings: _*)
  .settings(scalaSettings: _*)
  .settings(publishingSettings: _*)
  .settings(defaultSettings(): _*)
  .settings(routesImport += "controllers.Binders._")
  .settings(unmanagedResourceDirectories in Compile += baseDirectory.value / "resources")
  .settings(
    name := appName,
    scalaVersion := "2.11.11",
    libraryDependencies ++= appDependencies,
    retrieveManaged := true,
    evictionWarningOptions in update := EvictionWarningOptions.default.withWarnScalaVersionEviction(false),
    routesGenerator := StaticRoutesGenerator
  )
  .configs(IntegrationTest)
  .settings(inConfig(IntegrationTest)(Defaults.itSettings): _*)
  .settings(
    Keys.fork in IntegrationTest := false,
    unmanagedSourceDirectories in IntegrationTest <<= (baseDirectory in IntegrationTest) (base => Seq(base / "it")),
    addTestReportOption(IntegrationTest, "int-test-reports"),
    testGrouping in IntegrationTest := oneForkedJvmPerTest((definedTests in IntegrationTest).value),
    parallelExecution in IntegrationTest := false,
    libraryDependencies ++= test
  )
  .settings(resolvers ++= Seq(
    Resolver.bintrayRepo("hmrc", "releases"),
    Resolver.jcenterRepo
  ))

def oneForkedJvmPerTest(tests: Seq[TestDefinition]) =
  tests map {
    test => Group(test.name, Seq(test), SubProcess(ForkOptions(runJVMOptions = Seq("-Dtest.name=" + test.name))))
  }

