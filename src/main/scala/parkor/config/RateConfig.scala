package parkor.config

import java.time.format.DateTimeFormatter
import java.time.{DayOfWeek, LocalTime}

import io.circe.Decoder
import io.circe.magnolia.derivation.decoder.semiauto._
import parkor.domain.{Price, Rate}

case class RateConfig(rates: List[RateNode])

object RateConfig {
  implicit val decoder: Decoder[RateConfig] = deriveMagnoliaDecoder
}

case class RateNode(days: String, times: String, price: Int) {
  def toRate: Rate = {
    val daysOfWeek = parseDays(days)
    val (start, end) = parseTimes(times)

    Rate(daysOfWeek, start, end, Price(price))
  }

  private def parseDays(dayString: String): Set[DayOfWeek] = {
    import DayOfWeek._

    dayString.split(",").map {
      case "mon" => MONDAY
      case "tues" => TUESDAY
      case "wed" => WEDNESDAY
      case "thurs" => THURSDAY
      case "fri" => FRIDAY
      case "sat" => SATURDAY
      case "sun" => SUNDAY
    }.toSet
  }

  private def parseTimes(timesString: String): (LocalTime, LocalTime) = {
    val df = DateTimeFormatter.ofPattern("HHMM")
    val times = timesString.split("-").map(LocalTime.parse(_, df))
    (times(0), times(1))
  }

}

object RateNode {
  implicit val decoder: Decoder[RateNode] = deriveMagnoliaDecoder
}

