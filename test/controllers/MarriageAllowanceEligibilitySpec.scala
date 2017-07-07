/*
 * Copyright 2017 HM Revenue & Customs
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

import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play.OneAppPerSuite
import play.api.http.Status
import play.api.libs.json.Json
import play.api.test.FakeRequest
import uk.gov.hmrc.play.filters.MicroserviceFilterSupport
import uk.gov.hmrc.play.http.HeaderCarrier
import uk.gov.hmrc.play.test.UnitSpec
import util.ResourceLoader

class MarriageAllowanceEligibilitySpec extends UnitSpec with MockitoSugar with OneAppPerSuite with ResourceLoader {
  trait Setup extends MicroserviceFilterSupport {
    val request = FakeRequest().withHeaders("Accept" -> "application/vnd.hmrc.1.0+json")
    implicit val headerCarrier = HeaderCarrier()

    val underTest = new MarriageAllowanceEligibility()
  }

  "fetch" should {
    "return the eligible happy path response when called with a utr, AA000003D, firstname, surname, dateOfBirth and taxYear" in new Setup {

      val expected = loadResource("/resources/marriage-allowance-eligibility/happy_path_eligible.json")

      val result = await(underTest.fetch("1111111111", "AA000003D", "John", "Smith", "1981-01-31", "2014-15")(request))

      status(result) shouldBe Status.OK
      jsonBodyOf(result) shouldBe Json.parse(expected)
    }

    "return the ineligible happy path response when called with a utr, AA000004C, firstname, surname, dateOfBirth and taxYear" in new Setup {

      val expected = loadResource("/resources/marriage-allowance-eligibility/happy_path_ineligible.json")

      val result = await(underTest.fetch("2222222222", "AA000004C", "John", "Smith", "1981-01-31", "2014-15")(request))

      status(result) shouldBe Status.OK
      jsonBodyOf(result) shouldBe Json.parse(expected)
    }
  }
}
