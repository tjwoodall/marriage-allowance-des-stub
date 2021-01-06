/*
 * Copyright 2021 HM Revenue & Customs
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

package stubs

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock._
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import play.api.http.Status._
import uk.gov.hmrc.domain.Nino

trait ApiPlatformTestUserStub {
  val mock: WireMock

  def willReturnTheIndividual(nino: Nino) = {
    mock.register(get(urlPathEqualTo(s"/individuals/nino/$nino"))
        .willReturn(aResponse().withStatus(OK).withBody(
          s"""
             |{
             |  "individualDetails": {
             |    "firstName": "Heather",
             |    "lastName": "Ling",
             |    "dateOfBirth": "1983-09-18"
             |  },
             |  "nino": "WC885133C"
             |}
           """.stripMargin)))
  }

  def willNotFindTheIndividual() = {
    mock.register(get(urlPathMatching("/individuals/nino/([A-Z0-9]+)"))
        .willReturn(aResponse().withStatus(NOT_FOUND)))
  }

  def willReturnAnError() = {
    mock.register(get(urlPathMatching("/individuals/nino/([A-Z0-9]+)"))
        .willReturn(aResponse().withStatus(INTERNAL_SERVER_ERROR)))
  }
}

object ApiPlatformTestUserStub extends ApiPlatformTestUserStub {
  val port = 11112
  val server = new WireMockServer(WireMockConfiguration.wireMockConfig().port(port))
  val mock = new WireMock("localhost", port)
  val url = s"http://localhost:$port"
}
