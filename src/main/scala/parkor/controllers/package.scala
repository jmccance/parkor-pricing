package parkor

import java.time.ZonedDateTime

import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport
import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller, ToResponseMarshaller}
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.{FromStringUnmarshaller, Unmarshaller}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.Encoder
import parkor.services.RateServiceError
import parkor.xml.ScalaXmlEncoder

package object controllers {

  /** Unmarshaller to read date times from query-string parameters. */
  implicit val instantUM: FromStringUnmarshaller[ZonedDateTime] =
    Unmarshaller.strict[String, ZonedDateTime](ZonedDateTime.parse)

  /** The default entity unmarshaller.
    *
    * This API supports both JSON and XML, so both a JSON and an XML encoder
    * are required for any entity we might return.
    */
  implicit def defaultMarshaller[A](
    implicit jsonEncoder: Encoder[A],
    xmlEncoder: ScalaXmlEncoder[A]
  ): ToEntityMarshaller[A] =
    Marshaller.oneOf(
      FailFastCirceSupport.marshaller[A],
      ScalaXmlSupport.defaultNodeSeqMarshaller.compose[A](xmlEncoder(_))
    )

  /** Map RateServiceErrors to 400 responses. */
  implicit def errorMarshaller(
    implicit entityMarshaller: ToEntityMarshaller[RateServiceError]
  ): ToResponseMarshaller[RateServiceError] =
    entityMarshaller.map { entity =>
      HttpResponse(StatusCodes.BadRequest, entity = entity)
    }

}
