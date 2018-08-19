package parkor

import akka.http.scaladsl.server.{ExceptionHandler, HttpApp, Route}
import parkor.config.{ApplicationConfig, _}
import parkor.controllers.RateController
import parkor.cors.scaladsl.CorsDirectives
import parkor.services.RateServiceImpl

class Application(config: ApplicationConfig) extends HttpApp with CorsDirectives {

  private val rates = readRateConfig(config.rateConfig)

  // TODO Dependency injection
  private val rateController = new RateController(new RateServiceImpl(rates))

  override val routes: Route =
  // TODO: Really we just want some generic `parkorRoutes` method here that
  // is already configured with do whatever standard wrappers we want to do.
  //
  // This would include CORS for Swagger UI, metrics, etc. What we _don't_
  // want is a bunch of hard-coded stuff (like we have here) that requires
  // code changes to turn on and off.
    cors() {
      path("rates")(rateController.routes)
    }
}

object Boot {
  def main(args: Array[String]) {
    val config = pureconfig.loadConfigOrThrow[ApplicationConfig]("parkor")
    val app = new Application(config)

    app.startServer("localhost", config.port)
  }
}
