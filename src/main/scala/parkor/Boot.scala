package parkor

import java.time.DayOfWeek._
import java.time.LocalTime

import akka.http.scaladsl.model.headers.{HttpOrigin, HttpOriginRange}
import akka.http.scaladsl.server.{HttpApp, Route}
import parkor.controllers.RateController
import parkor.cors.scaladsl.{CorsDirectives, RequestIdDirective}
import parkor.domain.{Price, Rate}
import parkor.services.RateServiceImpl

object Boot extends HttpApp with CorsDirectives with RequestIdDirective {

  // TODO Dependency injection
  // TODO Load rates from config
  private val rateController = new RateController(new RateServiceImpl(
    Set(
      Rate(
        Set(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY),
        start = LocalTime.parse("06:00"),
        end = LocalTime.parse("18:00"),
        price = Price(1500)
      ),
      Rate(
        Set(SATURDAY, SUNDAY),
        start = LocalTime.parse("06:00"),
        end = LocalTime.parse("20:00"),
        price = Price(2000)
      )
    )
  ))

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

  def main(args: Array[String]) {
    Boot.startServer("localhost", 8080)
  }
}
