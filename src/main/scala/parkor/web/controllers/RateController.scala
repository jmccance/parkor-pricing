package parkor.web.controllers

import java.time.{Instant, ZonedDateTime}

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import parkor.services.RateService

class RateController(rateService: RateService) {

  val routes: Route =
    get {
      parameters(('start.as[ZonedDateTime], 'end.as[ZonedDateTime]))(getPrice)
    }

  def getPrice(start: ZonedDateTime, end: ZonedDateTime): Route = {
    val result =
      rateService
        .priceFor(start.toLocalDateTime, end.toLocalDateTime)
        .left
        .map { error =>
          ErrorResponse(
            Instant.now(),
            error.toString
          )
        }
        .map(r => RateResponse(r.map(_.value)))

    complete(result)
  }
}
