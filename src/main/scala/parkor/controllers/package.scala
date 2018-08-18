package parkor

import java.time.ZonedDateTime

import akka.http.scaladsl.marshalling.ToResponseMarshaller
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.unmarshalling.{FromStringUnmarshaller, Unmarshaller}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import parkor.services.RateServiceError

package object controllers extends FailFastCirceSupport {

  implicit val instantUM: FromStringUnmarshaller[ZonedDateTime] =
    Unmarshaller.strict[String, ZonedDateTime](ZonedDateTime.parse)

  implicit val errorMarshaller: ToResponseMarshaller[RateServiceError] =
    marshaller[RateServiceError].map { entity =>
      HttpResponse(StatusCodes.BadRequest, entity = entity)
    }

}
