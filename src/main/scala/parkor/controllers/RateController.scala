package parkor.controllers

import java.time.ZonedDateTime

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import parkor.services.RateService

class RateController(rateService: RateService) extends FailFastCirceSupport {

  val routes: Route =
    get {
      parameters(('start.as[ZonedDateTime], 'end.as[ZonedDateTime]))(getPrice)
    }

  def getPrice(start: ZonedDateTime, end: ZonedDateTime): Route = {
    val result =
      rateService
        .priceFor(start.toLocalDateTime, end.toLocalDateTime)
        .map(r => RateResponse(r.map(_.value)))

    complete(result)
  }
}
