package parkor.services

import io.circe.Encoder
import io.circe.magnolia.derivation.encoder.semiauto.deriveMagnoliaEncoder
import parkor.xml.ScalaXmlEncoder

sealed trait RateServiceError extends Product with Serializable

object RateServiceError {

  implicit val jsonEncoder: Encoder[RateServiceError] = deriveMagnoliaEncoder

  implicit val xmlEncoder: ScalaXmlEncoder[RateServiceError] = {
    case InvalidDateRangeError => <InvalidDateRangeError/>
    case UnsupportedDateRangeError => <UnsupportedDateRangeError/>
  }

  case object InvalidDateRangeError extends RateServiceError

  case object UnsupportedDateRangeError extends RateServiceError

}