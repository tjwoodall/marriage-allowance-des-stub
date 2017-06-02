package controllers

import common.StubResource
import play.api.mvc.Action
import uk.gov.hmrc.play.http.logging.MdcLoggingExecutionContext._
import uk.gov.hmrc.play.microservice.controller.BaseController

import scala.concurrent.Future

class MarriageAllowanceStatus extends BaseController with StubResource {
  def fetch(utr: String, taxYearStart: String) = Action.async {
    implicit request =>
      val dataSetPath = "/resources/marriage-allowance-status/happy_path.json"
      Future(jsonResourceAsResponse(dataSetPath))
  }
}
