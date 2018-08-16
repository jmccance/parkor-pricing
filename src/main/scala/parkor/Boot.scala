package parkor

import akka.http.scaladsl.server.{HttpApp, Route}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import parkor.cors.scaladsl.CorsDirectives

object Boot extends HttpApp with CorsDirectives with FailFastCirceSupport {

  override val routes: Route =
  // TODO: Really we just want some generic `parkorRoutes` method here that
  // is already configured with do whatever standard wrappers we want to do.
  //
  // This would include CORS for Swagger UI, metrics, etc. What we _don't_
  // want is a bunch of hard-coded stuff (like we have here) that requires
  // code changes to turn on and off.
    cors() {
      path("rates") {
        get {
          complete(RateResponse(Some(1500)))
        }
      }
    }

  def main(args: Array[String]) {
    Boot.startServer("localhost", 8080)
  }
}
