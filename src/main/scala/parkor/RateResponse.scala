package parkor

import io.circe.Encoder
import io.circe.magnolia.derivation.encoder.semiauto._

case class RateResponse(rate: Option[Int])

object RateResponse {
  implicit val encoder: Encoder[RateResponse] = deriveMagnoliaEncoder
}