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
import play.api.mvc.Action
import uk.gov.hmrc.domain.SaUtr
import uk.gov.hmrc.play.microservice.controller.BaseController

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class MarriageAllowanceStatus extends BaseController with StubResource {
  def fetch(utr: String, taxYearStart: String) = Action.async {
    implicit request =>
      val dataSetPath = "/resources/marriage-allowance-status/happy_path.json"
      Future(jsonResourceAsResponse(dataSetPath))
  }

  def create(utr: SaUtr, taxYear: TaxYear) = Action async {
    Future(Created)
  }
}
