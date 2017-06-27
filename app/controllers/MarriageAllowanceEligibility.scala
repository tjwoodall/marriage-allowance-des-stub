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
import play.api.mvc.Action
import uk.gov.hmrc.play.http.logging.MdcLoggingExecutionContext._
import uk.gov.hmrc.play.microservice.controller.BaseController

import scala.concurrent.Future

class MarriageAllowanceEligibility extends BaseController with StubResource {
  def fetch(nino: String, firstname: String, surname: String, dateOfBirth: String, taxYearStart: String) = Action.async {
    implicit request =>
      val eligibleDataSetPath = "/resources/marriage-allowance-eligibility/happy_path_eligible.json"
      val ineligibleDataSetPath = "/resources/marriage-allowance-eligibility/happy_path_ineligible.json"

      nino match {
        case "AA000003D" => Future(jsonResourceAsResponse(eligibleDataSetPath))
        case "AA000004C" => Future(jsonResourceAsResponse(ineligibleDataSetPath))
        case _ => Future.failed(new Exception(s"National Insurance Number $nino is not supported by this API"))
      }
  }
}
