/*
 * Copyright 2019 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package connectors

import config.WSHttp
import play.api.Mode.Mode
import play.api.{Configuration, Logger, Play}
import uk.gov.hmrc.api.connector.ServiceLocatorConnector
import uk.gov.hmrc.http.HttpPost
import uk.gov.hmrc.play.config.ServicesConfig

object ServiceLocatorConnector extends ServiceLocatorConnector with ServicesConfig {
  override protected def mode: Mode = Play.current.mode
  override protected def runModeConfiguration: Configuration = Play.current.configuration

  lazy val appName: String = runModeConfiguration.getString("appName").getOrElse(throw new RuntimeException("appName is not configured"))
  lazy val appUrl: String = runModeConfiguration.getString("appUrl").getOrElse(throw new RuntimeException("appUrl is not configured"))
  override lazy val serviceUrl: String = baseUrl("service-locator")
  override val http: HttpPost = WSHttp
  override val handlerOK: () => Unit = () => Logger.info("Service is registered on the service locator")
  override val handlerError: Throwable => Unit = e => Logger.error(s"Service could not register on the service locator", e)
  override val metadata: Option[Map[String, String]] = Some(Map("third-party-api" -> "true"))
}
