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

import play.api.libs.json.{Json, OFormat}

package object models {
  implicit val marriageAllowanceStatusCreationRequest: OFormat[MarriageAllowanceStatusCreationRequest] = Json.format[MarriageAllowanceStatusCreationRequest]
  implicit val marriageAllowanceStatusSummaryResponseFormat: OFormat[MarriageAllowanceStatusSummaryResponse] = Json.format[MarriageAllowanceStatusSummaryResponse]
  implicit val marriageAllowanceStatusSummaryFormat: OFormat[StatusSummary] = Json.format[StatusSummary]
  implicit val marriageAllowanceEligibilityCreationRequest: OFormat[MarriageAllowanceEligibilityCreationRequest] = Json.format[MarriageAllowanceEligibilityCreationRequest]
  implicit val marriageAllowanceEligibilitySummaryResponseFormat: OFormat[MarriageAllowanceEligibilitySummaryResponse] = Json.format[MarriageAllowanceEligibilitySummaryResponse]
  implicit val marriageAllowanceEligibilitySummaryFormat: OFormat[EligibilitySummary] = Json.format[EligibilitySummary]

  implicit val apiAccessFormat: OFormat[APIAccess] = Json.format[APIAccess]

  implicit val individualDetailsFormat: OFormat[IndividualDetails] = Json.format[IndividualDetails]
  implicit val testIndividualFormat: OFormat[TestIndividual] = Json.format[TestIndividual]
}
