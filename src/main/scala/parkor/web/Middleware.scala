package parkor.web

import akka.http.scaladsl.server.{Directive0, Route}

/** Marker trait to distinguish the directive we're using as middleware from any
  * other Directive0.
  *
  * This is done to make the bindings in [[parkor.config.ApplicationModule]]
  * clearer.
   */
trait Middleware extends Directive0

object Middleware {
  def apply(outer: Directive0): Middleware =
    (inner: Unit => Route) => outer.tapply(inner)
}
