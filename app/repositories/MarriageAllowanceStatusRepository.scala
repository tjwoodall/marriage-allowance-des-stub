/*
 * Copyright 2020 HM Revenue & Customs
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

package repositories

import com.google.inject.{Inject, Provider}
import models._
import play.modules.reactivemongo.{MongoDbConnection, ReactiveMongoComponent}
import reactivemongo.api.DB
import reactivemongo.bson.BSONObjectID
import uk.gov.hmrc.mongo.ReactiveRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

//trait MarriageAllowanceStatusRepository extends ReactiveRepository[StatusSummary, BSONObjectID] {
//  def store[T <: StatusSummary](marriageAllowanceStatusSummary: T): Future[T]
//  def fetch(utr: String, taxYear: String): Future[Option[StatusSummary]]
//}
//
//object MarriageAllowanceStatusRepository extends MongoDbConnection {
//  private lazy val repository = new StatusRepository
//  def apply(): MarriageAllowanceStatusRepository = repository
//}
//
//class StatusRepositoryProvider extends Provider[MarriageAllowanceStatusRepository] {
//  override def get(): MarriageAllowanceStatusRepository = MarriageAllowanceStatusRepository()
//}

class MarriageAllowanceStatusRepository @Inject()(reactiveMongoComponent: ReactiveMongoComponent)
  extends ReactiveRepository[StatusSummary, BSONObjectID]("marriage-allowance-status", reactiveMongoComponent.mongoConnector.db,
  marriageAllowanceStatusSummaryFormat, objectIdFormat) {
  def store[T <: StatusSummary](marriageAllowanceStatusSummary: T): Future[T] =
    for{
      _ <- remove("utr" -> marriageAllowanceStatusSummary.utr, "taxYear" -> marriageAllowanceStatusSummary.taxYear)
      _ <- insert(marriageAllowanceStatusSummary)
    } yield marriageAllowanceStatusSummary

  def fetch(utr: String, taxYear: String): Future[Option[StatusSummary]] =
    find("utr" -> utr, "taxYear" -> taxYear) map(_.headOption)
}
