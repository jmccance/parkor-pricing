package parkor.web.controllers

import java.time.Instant

import io.circe.Encoder
import io.circe.magnolia.derivation.encoder.semiauto._
import parkor.xml.ScalaXmlEncoder

case class RateResponse(rate: Option[Int])

object RateResponse {

  implicit val jsonEncoder: Encoder[RateResponse] = deriveMagnoliaEncoder

  implicit val xmlEncoder: ScalaXmlEncoder[RateResponse] = { response =>
    <RateResponse>
      <rate>
        {response.rate.orNull}
      </rate>
    </RateResponse>
  }

}

case class ErrorResponse(
  timestamp: Instant,
  error: String
)

object ErrorResponse {
  implicit val jsonEncoder: Encoder[ErrorResponse] = deriveMagnoliaEncoder

  implicit val xmlEncoder: ScalaXmlEncoder[ErrorResponse] = { response =>
    <ErrorResponse>
      <timestamp>
        {response.timestamp}
      </timestamp>
      <error>
        {response.error}
      </error>
    </ErrorResponse>
  }
}