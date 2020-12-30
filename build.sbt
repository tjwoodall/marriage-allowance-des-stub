import de.heikoseeberger.sbtheader.AutomateHeaderPlugin.autoImport.automateHeaderSettings
import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport.headerSettings
import uk.gov.hmrc.DefaultBuildSettings._
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin._
import uk.gov.hmrc.versioning.SbtGitVersioning


lazy val appName = "marriage-allowance-des-stub"

lazy val microservice = (project in file("."))
  .enablePlugins(PlayScala, SbtAutoBuildPlugin, SbtGitVersioning, SbtDistributablesPlugin, SbtArtifactory)
  .configs(IntegrationTest)
  .settings(
    integrationTestSettings(),
    headerSettings(IntegrationTest),
    automateHeaderSettings(IntegrationTest),
    scalaSettings,
    majorVersion := 0,
    publishingSettings,
    defaultSettings(),
    routesImport += "controllers.Binders._",
    name := appName,
    libraryDependencies ++= AppDependencies.all,
    retrieveManaged := true,
    unmanagedResourceDirectories in Compile += baseDirectory.value / "resources",
    evictionWarningOptions in update := EvictionWarningOptions.default.withWarnScalaVersionEviction(false),
    resolvers ++= Seq(
        Resolver.bintrayRepo("hmrc", "releases"),
        Resolver.jcenterRepo
    )
  )

// Coverage configuration
coverageMinimum := 75
coverageFailOnMinimum := true
coverageExcludedPackages := "<empty>;com.kenshoo.play.metrics.*;.*definition.*;prod.*;testOnlyDoNotUseInAppConf.*;app.*;uk.gov.hmrc.BuildInfo"

