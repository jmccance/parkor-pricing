package parkor.services

import java.time._

import parkor.domain.{Price, Rate}
import parkor.services.RateServiceError.InvalidDateRangeError

trait RateService {
  def priceFor(
    start: LocalDateTime,
    end: LocalDateTime
  ): Either[RateServiceError, Option[Price]]
}

class RateServiceImpl(private val rates: Set[Rate]) extends RateService {

  import RateService._

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

object RateService {
  def getDayOfWeek(
    start: LocalDateTime,
    end: LocalDateTime
  ): Either[RateServiceError, DayOfWeek] =
    if (start.getDayOfWeek != end.getDayOfWeek) {
      Left(InvalidDateRangeError)
    } else {
      Right(start.getDayOfWeek)
    }

  def getRateForTime(
    ratesForDay: Iterable[Rate],
    start: LocalDateTime,
    end: LocalDateTime
  ): Option[Rate] = {
    ratesForDay.find { rate =>
      rate.start.isBefore(start.toLocalTime) &&
        rate.end.isAfter(end.toLocalTime)
    }
  }
}
