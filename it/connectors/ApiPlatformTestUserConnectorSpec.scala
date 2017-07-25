package connectors

import models.{IndividualDetails, TestIndividual}
import org.joda.time.LocalDate
import org.scalatest.BeforeAndAfterEach
import stubs.ApiPlatformTestUserStub
import uk.gov.hmrc.domain.Nino
import uk.gov.hmrc.play.http.{HeaderCarrier, NotFoundException, Upstream5xxResponse}
import uk.gov.hmrc.play.test.{UnitSpec, WithFakeApplication}

class ApiPlatformTestUserConnectorSpec extends UnitSpec with BeforeAndAfterEach with WithFakeApplication {

  val nino = Nino("WC885133C")
  val individual = TestIndividual(nino, IndividualDetails("Heather", "Ling", LocalDate.parse("1983-09-18")))

  trait Setup {
    implicit val hc = HeaderCarrier()

    val underTest = new ApiPlatformTestUserConnector {
      override lazy val serviceUrl = ApiPlatformTestUserStub.url
    }
  }

  override def beforeAll() = {
    super.beforeAll()
    ApiPlatformTestUserStub.server.start()
  }

  override def beforeEach() = {
    super.beforeEach()
    ApiPlatformTestUserStub.server.resetMappings()
  }

  override def afterAll() = {
    super.afterAll()
    ApiPlatformTestUserStub.server.stop()
  }

  "fetchIndividual" should {
    "return the individual when a NINO that exists is specified" in new Setup {
      ApiPlatformTestUserStub.willReturnTheIndividual(nino)

      val result = await(underTest.fetchByNino(nino))

      result shouldBe individual
    }

    "fail when the NINO does not exist" in new Setup {
      ApiPlatformTestUserStub.willNotFindTheIndividual()

      intercept[NotFoundException] {
        await(underTest.fetchByNino(nino))
      }
    }

    "fail when the remote service returns an error" in new Setup {
      ApiPlatformTestUserStub.willReturnAnError()

      intercept[Upstream5xxResponse] {
        await(underTest.fetchByNino(nino))
      }
    }
  }
}
