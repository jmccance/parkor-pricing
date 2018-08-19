package parkor.web

import akka.http.scaladsl.server._
import parkor.controllers.RateController

class Server(
  middleware: Directive0,
  rateController: RateController
) extends HttpApp {
  override val routes: Route =
    middleware {
      path("rates")(rateController.routes) ~
        pathPrefix("")(getFromResourceDirectory("public"))
    }
}
