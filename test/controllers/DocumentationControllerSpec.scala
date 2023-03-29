/*
 * Copyright 2023 HM Revenue & Customs
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

package controllers

import akka.stream.Materializer
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.HeaderNames.CONTENT_TYPE
import play.api.http.Status.{NOT_FOUND, OK}
import play.api.test.Helpers.{contentAsString, defaultAwaitTimeout, headers, status}
import play.api.test.{FakeRequest, Injecting}
import utils.ResourceProvider

class DocumentationControllerSpec extends PlaySpec with GuiceOneAppPerSuite with Injecting with ResourceProvider {

  implicit val materializer: Materializer          = app.materializer

  lazy val controller: DocumentationController = inject[DocumentationController]
  def applicationRamlContent(version: String): String = getResourceFileContent(s"/public/api/conf/$version/application.yaml")


  "DocumentationController" must {
    "return" when {

      val definitionVersions = Seq("1.0", "2.0")
      definitionVersions foreach { version =>

        s"200 when yaml is called for version $version" in {
          val result = controller.yaml(version, "application.yaml")(FakeRequest("GET", s"/api/conf/$version/application.yaml"))

          status(result) mustBe OK
        }
        s"the correct yaml is returned for version $version" in {
          val result = controller.yaml(version, "application.yaml")(FakeRequest("GET", s"/api/conf/$version/application.yaml"))

          contentAsString(result) mustBe applicationRamlContent(version)
        }
      }

      "NOT_FOUND when invalid version of the yaml is called" in {
        val result = controller.yaml("99999.0", "application.yaml")(FakeRequest())

        status(result) mustBe NOT_FOUND
      }
    }
    "return a Json definition" in {
      val result = controller.definition(FakeRequest("GET", "/api/definition"))

      status(result) mustBe OK
      headers(result) must contain(CONTENT_TYPE -> "application/json;charset=utf-8")
    }
  }
}
