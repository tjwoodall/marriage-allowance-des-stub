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
