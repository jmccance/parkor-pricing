package parkor

import java.time.{DayOfWeek, LocalDateTime}

import parkor.domain._
import parkor.services.RateServiceError.InvalidDateRangeError

package object services {
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
