package parkor.web.controllers

import io.circe.Encoder
import io.circe.magnolia.derivation.encoder.semiauto._
import parkor.xml.ScalaXmlEncoder

case class RateResponse(rate: Option[Int])

object RateResponse {

  implicit val jsonEncoder: Encoder[RateResponse] = deriveMagnoliaEncoder

  implicit val xmlEncoder: ScalaXmlEncoder[RateResponse] = { response =>
    <RateResponse>
      <rate>{response.rate.orNull}</rate>
    </RateResponse>
  }

}

case class RateError()