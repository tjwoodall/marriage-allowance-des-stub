package config

import com.google.inject.{Inject, Singleton}
import com.typesafe.config.Config
import net.ceedubs.ficus.Ficus._
import play.api._
import play.api.http.DefaultHttpFilters
import uk.gov.hmrc.play.config.ControllerConfig
import uk.gov.hmrc.play.filters.MicroserviceFilterSupport
import uk.gov.hmrc.play.http.logging.filters.LoggingFilter

@Singleton
class ControllerConfiguration @Inject()(configuration: Configuration) extends ControllerConfig {

  lazy val controllerConfigs = configuration.underlying.as[Config]("controllers")
}

@Singleton
class MicroserviceLoggingFilter @Inject()(controllerConfiguration: ControllerConfiguration)
  extends LoggingFilter with MicroserviceFilterSupport {

  override def controllerNeedsLogging(controllerName: String) = controllerConfiguration.paramsForController(controllerName).needsLogging
}

@Singleton
class Filters @Inject() (loggingFilter: MicroserviceLoggingFilter) extends DefaultHttpFilters (loggingFilter)