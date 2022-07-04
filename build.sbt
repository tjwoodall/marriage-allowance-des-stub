import de.heikoseeberger.sbtheader.AutomateHeaderPlugin.autoImport.automateHeaderSettings
import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport.headerSettings
import uk.gov.hmrc.DefaultBuildSettings._
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin._
import uk.gov.hmrc.versioning.SbtGitVersioning

lazy val appName = "marriage-allowance-des-stub"
val silencerVersion = "1.7.6"

lazy val microservice = (project in file("."))
  .enablePlugins(PlayScala, SbtAutoBuildPlugin, SbtGitVersioning, SbtDistributablesPlugin, SbtArtifactory)
  .configs(IntegrationTest)
  .settings(
    integrationTestSettings(),
    headerSettings(IntegrationTest),
    automateHeaderSettings(IntegrationTest),
    scalaSettings,
    scalaVersion := "2.12.15",
    majorVersion := 0,
    publishingSettings,
    defaultSettings(),
    routesImport += "controllers.Binders._",
    name := appName,
    libraryDependencies ++= AppDependencies.all,
    retrieveManaged := true,
    Compile / unmanagedResourceDirectories += baseDirectory.value / "resources",
    update / evictionWarningOptions := EvictionWarningOptions.default.withWarnScalaVersionEviction(false),
    resolvers ++= Seq(
        Resolver.jcenterRepo
    )
  ).settings(
    scalacOptions ++= Seq(
      "-feature",
      "-Xfatal-warnings",
      "-P:silencer:pathFilters=routes,silencer:pathFilters=twirl"
    ),
    libraryDependencies ++= Seq(
        compilerPlugin("com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full),
        "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full
    )
)

// Coverage configuration
coverageMinimumStmtTotal := 75
coverageFailOnMinimum := true
coverageExcludedPackages := "<empty>;com.kenshoo.play.metrics.*;.*definition.*;prod.*;testOnlyDoNotUseInAppConf.*;app.*;uk.gov.hmrc.BuildInfo"

