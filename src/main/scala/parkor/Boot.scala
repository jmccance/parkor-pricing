package parkor

import akka.http.scaladsl.server.{HttpApp, Route}
import io.circe.parser._
import parkor.config.{ApplicationConfig, RateConfig}
import parkor.controllers.RateController
import parkor.cors.scaladsl.CorsDirectives
import parkor.services.RateServiceImpl

import scala.io.Source

class Application(config: ApplicationConfig) extends HttpApp with CorsDirectives {

  private val rates = {
    val jsonString = Source.fromFile(config.ratesFile).mkString
    val rateConfig =
      for {
        rateConfig <- decode[RateConfig](jsonString)
      } yield rateConfig.rates.map(_.toRate)

    rateConfig.getOrElse {
      throw new RuntimeException("fuck")
    }.toSet
  }

  // TODO Dependency injection
  // TODO Load rates from config
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
