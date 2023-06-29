import de.heikoseeberger.sbtheader.AutomateHeaderPlugin.autoImport.automateHeaderSettings
import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport.headerSettings
import uk.gov.hmrc.DefaultBuildSettings._
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin
import uk.gov.hmrc.versioning.SbtGitVersioning

lazy val appName = "marriage-allowance-des-stub"

lazy val microservice = (project in file("."))
  .enablePlugins(PlayScala, SbtAutoBuildPlugin, SbtGitVersioning, SbtDistributablesPlugin)
  .configs(IntegrationTest)
  .settings(
    integrationTestSettings(),
    headerSettings(IntegrationTest),
    automateHeaderSettings(IntegrationTest),
    scalaSettings,
    scalaVersion := "2.13.10",
    majorVersion := 0,
    defaultSettings(),
    routesImport += "controllers.Binders._",
    name := appName,
    libraryDependencies ++= AppDependencies.all,
    retrieveManaged := true,
    Compile / unmanagedResourceDirectories += baseDirectory.value / "resources",
    update / evictionWarningOptions := EvictionWarningOptions.default.withWarnScalaVersionEviction(false),
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
coverageMinimumStmtTotal := 17.27
coverageFailOnMinimum := true
coverageExcludedPackages := "<empty>;com.kenshoo.play.metrics.*;.*definition.*;prod.*;testOnlyDoNotUseInAppConf.*;app.*;uk.gov.hmrc.BuildInfo;.*Routes.*;.*config.*;"