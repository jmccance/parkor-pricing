package parkor.services

import java.time._

import parkor.domain.{Price, Rate}

trait RateService {
  def priceFor(
    start: LocalDateTime,
    end: LocalDateTime
  ): Either[RateServiceError, Option[Price]]
}

class RateServiceImpl(private val rates: Set[Rate]) extends RateService {

  override def priceFor(
    start: LocalDateTime,
    end: LocalDateTime
  ): Either[RateServiceError, Option[Price]] = {
    for {
      dayOfWeek <- getDayOfWeek(start, end)
      ratesForDay <- Right(rates.filter(_.days.contains(dayOfWeek)))
      rateForTime <- Right(getRateForTime(ratesForDay, start, end))
    } yield rateForTime.map(_.price)
  }
}

sealed trait RateServiceError extends Product with Serializable

object RateServiceError {

  case object InvalidDateRangeError extends RateServiceError

}
