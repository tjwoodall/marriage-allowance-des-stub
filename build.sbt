import uk.gov.hmrc.DefaultBuildSettings._
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin

lazy val appName = "marriage-allowance-des-stub"


ThisBuild / scalaVersion := "2.13.14"
ThisBuild / majorVersion := 0

lazy val microservice = (project in file("."))
  .enablePlugins(PlayScala, SbtDistributablesPlugin)
  .settings(
    PlayKeys.playDefaultPort := 9687,
    scalaSettings,
    defaultSettings(),
    routesImport += "controllers.Binders._",
    name := appName,
    libraryDependencies ++= AppDependencies.all,
    retrieveManaged := true,
    Compile / unmanagedResourceDirectories += baseDirectory.value / "resources",
    ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always,
    resolvers ++= Seq(
      Resolver.jcenterRepo
    )
  ).settings(
  scalacOptions ++= Seq(
    "-feature",
    "-Werror",
    "-Wconf:cat=unused-imports&site=.*views\\.html.*:s",
    "-Wconf:cat=unused-imports&site=.*views\\.txt.*:s",
    "-Wconf:cat=unused-imports&site=<empty>:s",
    "-Wconf:cat=unused&src=.*RoutesPrefix\\.scala:s",
    "-Wconf:cat=unused&src=.*Routes\\.scala:s",
    "-Wconf:cat=unused&src=.*ReverseRoutes\\.scala:s",
    "-Wconf:cat=unused&src=.*JavaScriptReverseRoutes\\.scala:s"
  ),
)
// Coverage configuration
val ScoverageExclusionPatterns = List(
  "<empty>",
  ".*definition.*",
  "prod.*",
  "testOnlyDoNotUseInAppConf.*",
  "app.*",
  "uk.gov.hmrc.BuildInfo",
  ".*Routes.*",
  ".*config.*"
)
coverageMinimumStmtTotal := 94.00
coverageFailOnMinimum := true
coverageExcludedPackages := ScoverageExclusionPatterns.mkString("", ";", "")

val it: Project = project
  .enablePlugins(PlayScala)
  .dependsOn(microservice % "test->test")
  .settings(itSettings())

addCommandAlias("runAllTests", ";test;it/test;")
addCommandAlias("runAllChecks", ";clean;coverageOn;runAllTests;coverageOff;coverageAggregate;")
