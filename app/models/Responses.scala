/*
 * Copyright 2023 HM Revenue & Customs
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

package models

import play.api.libs.json._

sealed trait MarriageAllowanceResponse

final case class MarriageAllowanceStatusSummaryResponse(status: String, deceased: Boolean) extends MarriageAllowanceResponse
final case class MarriageAllowanceEligibilitySummaryResponse(eligible: Boolean) extends MarriageAllowanceResponse

object MarriageAllowanceResponse {
  implicit val marriageAllowanceStatusSummaryResponseFormat: OFormat[MarriageAllowanceStatusSummaryResponse] = Json.format[MarriageAllowanceStatusSummaryResponse]
  implicit val marriageAllowanceEligibilitySummaryResponseFormat: OFormat[MarriageAllowanceEligibilitySummaryResponse] = Json.format[MarriageAllowanceEligibilitySummaryResponse]

  implicit val marriageAllowanceResponseFormat: Format[MarriageAllowanceResponse] = new Format[MarriageAllowanceResponse] {
    override def reads(json: JsValue): JsResult[MarriageAllowanceResponse] = {
      (json \ "status").validate[String].map { _ =>
        Json.fromJson[MarriageAllowanceStatusSummaryResponse](json).getOrElse {
          Json.fromJson[MarriageAllowanceEligibilitySummaryResponse](json).getOrElse {
            throw new RuntimeException("Unknown response type")
          }
        }
      }
    }

    override def writes(o: MarriageAllowanceResponse): JsValue = o match {
      case statusSummary: MarriageAllowanceStatusSummaryResponse => Json.toJson(statusSummary)
      case eligibilitySummary: MarriageAllowanceEligibilitySummaryResponse => Json.toJson(eligibilitySummary)
    }
  }
}
