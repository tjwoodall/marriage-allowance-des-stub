import sbt._
import play.sbt.PlayImport.ws

object AppDependencies {

  private val scalatestVersion = "3.2.16"
  private val scalatestplusVersion = "5.1.0"
  private val flexmarkallVersion = "0.62.2"
  private val hmrcMongoVersion = "1.1.0"
  private val bootstrapVersion = "7.15.0"
  private val playSuffix = "play-28"

  lazy val compile: Seq[ModuleID] = Seq(
    ws,
    "uk.gov.hmrc"          %% "bootstrap-backend-play-28" % bootstrapVersion,
    "uk.gov.hmrc"          %% "domain"                    % s"8.2.0-$playSuffix",
    "uk.gov.hmrc.mongo"    %% s"hmrc-mongo-$playSuffix"   % hmrcMongoVersion
  )

  lazy val test: Seq[ModuleID] = Seq(
    "org.scalatest"          %% "scalatest"                    % scalatestVersion,
    "org.scalatestplus.play" %% "scalatestplus-play"           % scalatestplusVersion,
    "org.mockito"             % "mockito-core"                 % "5.2.0",
    "com.vladsch.flexmark"    % "flexmark-all"                 % flexmarkallVersion,
    "uk.gov.hmrc.mongo"      %% s"hmrc-mongo-test-$playSuffix" % hmrcMongoVersion,
    "uk.gov.hmrc"            %% s"bootstrap-test-$playSuffix"  % bootstrapVersion,
  ).map(_ % "test")

  val itTest: Seq[ModuleID] = Seq(
    "org.scalatest"                %% "scalatest"                   % scalatestVersion,
    "org.scalatestplus.play"       %% "scalatestplus-play"          % scalatestplusVersion,
    "org.scalaj"                   %% "scalaj-http"                 % "2.4.2",
    "com.github.tomakehurst"        % "wiremock-jre8"               % "2.35.0",
    "com.vladsch.flexmark"          % "flexmark-all"                % flexmarkallVersion,
    "com.fasterxml.jackson.module" %% "jackson-module-scala"        % "2.14.2",
    "uk.gov.hmrc"                  %% s"bootstrap-test-$playSuffix" % bootstrapVersion,
  ).map(_ % "it")

  val all: Seq[ModuleID] = compile ++ test ++ itTest
}
