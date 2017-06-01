package controllers

import common.StubResource
import play.api.mvc.Action
import uk.gov.hmrc.play.http.logging.MdcLoggingExecutionContext._
import uk.gov.hmrc.play.microservice.controller.BaseController

import scala.concurrent.Future

class MarriageAllowanceEligibility extends BaseController with StubResource {
  def fetch(utr: String, nino: String, firstname: String, surname: String, dateOfBirth: String, taxYear: String) = Action.async {
    implicit request =>
      val dataSetPath = "/resources/marriage-allowance-eligibility/happy_path.json"
      Future(jsonResourceAsResponse(dataSetPath))
  }
}
