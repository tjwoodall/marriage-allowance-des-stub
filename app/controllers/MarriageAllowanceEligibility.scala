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

import common.StubResource
import models.TaxYear
import play.api.mvc._
import uk.gov.hmrc.domain.SaUtr
import uk.gov.hmrc.play.microservice.controller.BaseController

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

final class MarriageAllowanceEligibility extends BaseController with StubResource {
  def fetch(utr: String, nino: String, firstname: String, surname: String, dateOfBirth: String, taxYearStart: String) = Action async {
    implicit request =>
      val eligibleDataSetPath = "/resources/marriage-allowance-eligibility/happy_path_eligible.json"
      val ineligibleDataSetPath = "/resources/marriage-allowance-eligibility/happy_path_ineligible.json"

      utr match {
        case "1111111111" => Future(jsonResourceAsResponse(eligibleDataSetPath))
        case "2222222222" => Future(jsonResourceAsResponse(ineligibleDataSetPath))
        case _ => Future.failed(new Exception(s"National Insurance Number $nino is not supported by this API"))
      }
  }

  def create(utr: SaUtr, taxYear: TaxYear) = Action async {
    Future(Created)
  }
}
