import sbt._
import play.sbt.PlayImport.ws

object AppDependencies {

  private val scalatestVersion = "3.2.16"
  private val scalatestplusVersion = "5.1.0"
  private val flexmarkallVersion = "0.62.2"
  private val hmrcMongoVersion = "1.7.0"
  private val bootstrapVersion = "8.4.0"
  private val playSuffix = "play-30"

  lazy val compile: Seq[ModuleID] = Seq(
    ws,
    "uk.gov.hmrc"          %% s"bootstrap-backend-$playSuffix" % bootstrapVersion,
    "uk.gov.hmrc"          %% s"domain-$playSuffix"                    % "9.0.0",
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
    "org.wiremock"                  % "wiremock"                    % "3.3.1",
    "com.vladsch.flexmark"          % "flexmark-all"                % flexmarkallVersion,
    "com.fasterxml.jackson.module" %% "jackson-module-scala"        % "2.16.1",
    "uk.gov.hmrc"                  %% s"bootstrap-test-$playSuffix" % bootstrapVersion,
  ).map(_ % "it")

  val all: Seq[ModuleID] = compile ++ test ++ itTest
}
