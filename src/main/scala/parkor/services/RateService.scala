package parkor.services

import java.time._

import parkor.domain.{Price, Rate}
import parkor.services.RateServiceError.{InvalidDateRangeError, UnsupportedDateRangeError}

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
    validateDateRange(start, end).map { _ =>
      for {
        dayOfWeek <- getDayOfWeek(start, end)
        ratesForDay <- Some(getRatesForDay(rates, dayOfWeek))
        rateForTime <-
          getRateForTime(
            ratesForDay,
            start.toLocalTime,
            end.toLocalTime
          )
      } yield rateForTime.price
    }
  }
}

object RateService {

  def validateDateRange(
    start: LocalDateTime,
    end: LocalDateTime
  ): Either[RateServiceError, Unit] =
    if (start.isAfter(end)) {
      // Ranges that end before they start are invalid
      Left(InvalidDateRangeError)
    } else if (
      Duration.between(start, end).compareTo(Duration.ofDays(1L)) > 0
    ) {
      // Ranges that span days are valid, but not meaningful.
      Left(UnsupportedDateRangeError)
    } else {
      // Otherwise, the range is valid
      Right(Some(()))
    }

  /** Get the day of the week for which we are calculating the hourly rate.
    *
    * Only returns values for ranges that do not span days.
    *
    * @param start start of date range
    * @param end   end of date range
    * @return the DayOfWeek for this range, or None if the range spans multiple
    *         days
    */
  def getDayOfWeek(
    start: LocalDateTime,
    end: LocalDateTime
  ): Option[DayOfWeek] =
    if (start.getDayOfWeek != end.getDayOfWeek) None
    else Some(start.getDayOfWeek)

  def getRatesForDay(rates: Iterable[Rate], dayOfWeek: DayOfWeek): Iterable[Rate] =
    rates.filter(_.days.contains(dayOfWeek))

  /** Given a set of rates for a specific day of the week.
    *
    * @param ratesForDay the set of rates to search, which is assumed to be for
    *                    the right day of the week
    * @param start
    * @param end
    * @return
    */
  def getRateForTime(
    ratesForDay: Iterable[Rate],
    start: LocalTime,
    end: LocalTime
  ): Option[Rate] =
    ratesForDay.find { rate =>
      rate.start.isBefore(start) && rate.end.isAfter(end)
    }
}
