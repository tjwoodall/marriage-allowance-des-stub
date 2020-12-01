import sbt._
import play.sbt.PlayImport.ws

object AppDependencies {

  val hmrcTestVersion = "3.9.0-play-25" //TODO remove
  val scalaTestPlusPlayVersion = "2.0.1"

  lazy val compile: Seq[ModuleID] = Seq(
    ws,
    "uk.gov.hmrc" %% "microservice-bootstrap" % "10.4.0",
    "uk.gov.hmrc" %% "domain" % "5.10.0-play-25",
    "uk.gov.hmrc" %% "play-reactivemongo" % "6.8.0"
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
  ).map(_ % "it") //TODO refactor

  val all: Seq[ModuleID] = compile ++ test ++ itTest
}
