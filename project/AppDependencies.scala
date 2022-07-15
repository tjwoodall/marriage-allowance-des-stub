import sbt._
import play.sbt.PlayImport.ws

object AppDependencies {

  lazy val compile: Seq[ModuleID] = Seq(
    ws,
    "uk.gov.hmrc"          %% "bootstrap-backend-play-28" % "6.3.0",
    "uk.gov.hmrc"          %% "domain"                    % "8.1.0-play-28",
    "uk.gov.hmrc.mongo"    %% "hmrc-mongo-play-28"        % "0.67.0"
  )

  lazy val test: Seq[ModuleID] = Seq(
    "org.scalatest"          %% "scalatest"          % "3.2.12",
    "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0",
    "org.mockito"             % "mockito-core"       % "4.6.1",
    "com.vladsch.flexmark"    % "flexmark-all"       % "0.62.2"
  ).map(_ % "test")

  val itTest: Seq[ModuleID] = Seq(
    "org.scalatest"                %% "scalatest"            % "3.2.12",
    "org.scalatestplus.play"       %% "scalatestplus-play"   % "5.1.0",
    "org.scalaj"                   %% "scalaj-http"          % "2.4.2",
    "com.github.tomakehurst"        % "wiremock-jre8"        % "2.33.2",
    "com.vladsch.flexmark"          % "flexmark-all"         % "0.62.2",
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.13.3"
  ).map(_ % "it")

  val all: Seq[ModuleID] = compile ++ test ++ itTest
}
