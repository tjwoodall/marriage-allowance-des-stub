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
