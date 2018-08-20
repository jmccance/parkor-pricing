package parkor.web.directives

import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.server.Directive0
import akka.http.scaladsl.server.Directives._

import scala.util.{Success, Try}

trait CustomDirectives {
  def cors(): Directive0 =
    respondWithHeaders(`Access-Control-Allow-Origin`(HttpOriginRange.*))

  def withRequestId: Directive0 =
    mapInnerRoute { route =>
      respondWithDefaultHeader {
        // TODO Get the requestId onto logging context.
        // Ideally the requestId would be included in the logging context. However,
        // we can't directly use the MDC because Akka doesn't use a single-threaded
        // request model.
        //
        // I suspect the answer is to use `withLog` to thread a new LoggingAdapter
        // down through the routing DSL that simply carries its context with it,
        // instead of relying on thread-locals.

        def randomRequestId(): String = {
          val uuid = java.util.UUID.randomUUID()
          uuid.getMostSignificantBits.toHexString +
            uuid.getLeastSignificantBits.toHexString
        }

        RequestId(randomRequestId())
      } {
        route
      }
    }
}

object CustomDirectives extends CustomDirectives

class RequestId(requestId: String) extends ModeledCustomHeader[RequestId] {
  override def companion: ModeledCustomHeaderCompanion[RequestId] = RequestId

  override def value(): String = requestId

  override def renderInRequests(): Boolean = false

  override def renderInResponses(): Boolean = true
}

object RequestId extends ModeledCustomHeaderCompanion[RequestId] {
  override def name: String = "Request-Id"

  override def parse(value: String): Try[RequestId] = Success(new RequestId(value))
}