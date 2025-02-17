import uk.gov.hmrc.DefaultBuildSettings._
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin

lazy val appName = "marriage-allowance-des-stub"


ThisBuild / scalaVersion := "3.6.2"
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
    "-Xfatal-warnings",
    "-Wconf:src=routes/.*:silent",
    "-Wconf:src=twirl/.*:silent",
    "-Wconf:src=target/.*:silent",
    "-Wconf:msg=Flag.*repeatedly:silent",
    "-Wconf:msg=.*-Wunused.*:silent",
  )


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
