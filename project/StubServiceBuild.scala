import sbt._
import play.sbt.PlayImport._
import play.core.PlayVersion
import uk.gov.hmrc.SbtAutoBuildPlugin
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin
import uk.gov.hmrc.versioning.SbtGitVersioning

object StubServiceBuild extends Build with MicroService {
  import scala.util.Properties.envOrElse

  val appName = "marriage-allowance-des-stub"
  val appVersion = envOrElse("PAYE_DES_STUB_VERSION", "999-SNAPSHOT")

  override lazy val appDependencies: Seq[ModuleID] = AppDependencies()
}

private object AppDependencies {
  import play.sbt.PlayImport._
  import play.core.PlayVersion

  val compile = Seq(
    ws,
    "uk.gov.hmrc" %% "microservice-bootstrap" % "5.13.0",
    "uk.gov.hmrc" %% "play-health" % "2.1.0",
    "uk.gov.hmrc" %% "play-config" % "4.2.0",
    "uk.gov.hmrc" %% "logback-json-logger" % "3.1.0"
  )

  trait TestDependencies {
    lazy val scope: String = "test"
    val test : Seq[ModuleID]
  }

  object Test {
    def apply() = new TestDependencies {
      override lazy val test = Seq(
        "uk.gov.hmrc" %% "hmrctest" % "2.3.0" % scope,
        "org.scalatest" %% "scalatest" % "2.2.6" % scope,
        "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % scope,
        "org.mockito" % "mockito-core" % "1.10.19" % scope,
        "org.pegdown" % "pegdown" % "1.6.0" % scope,
        "com.typesafe.play" %% "play-test" % PlayVersion.current % scope
      )
    }.test
  }

  def apply() = compile ++ Test()
}