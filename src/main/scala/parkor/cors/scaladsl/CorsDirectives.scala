package parkor.cors.scaladsl

import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.server.Directive0
import akka.http.scaladsl.server.Directives._

trait CorsDirectives {
  def cors(): Directive0 =
    respondWithHeaders(`Access-Control-Allow-Origin`(HttpOriginRange.*))
}

object CorsDirectives extends CorsDirectives