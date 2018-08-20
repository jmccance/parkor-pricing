package parkor

import java.time.Instant

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{MalformedQueryParamRejection, RejectionHandler}
import parkor.web.controllers.ErrorResponse

package object web {
  val rejectionHandler: RejectionHandler =
    RejectionHandler.newBuilder()
      .handle {
        case MalformedQueryParamRejection(name, msg, _) =>
          complete(
            ErrorResponse(
              Instant.now(),
              s"The query parameter '$name' was malformed: $msg"
            )
          )
      }
      .result()
}
