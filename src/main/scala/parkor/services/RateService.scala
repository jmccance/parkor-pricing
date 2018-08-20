package parkor.services

import java.time._

import cats.data.OptionT
import cats.instances.either._
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
    type E[A] = Either[RateServiceError, A]
    import OptionT._

    // Since we're dealing with a mixture of errors and just not having a value
    // (when there is no available rate), we make use of the OptionT type to
    // simplify this logic.
    //
    // See https://typelevel.org/cats/datatypes/optiont.html for more
    // information, but the short version is that we write functions that deal
    // exactly with our domain of interest (Option, Either, or even pure values)
    // and then use the methods imported from OptionT to handle the mechanical
    // plumbing of threading those through the types.
    val result = for {
      _ <- some[E](validateDateRange(start, end))
      dayOfWeek <- fromOption[E](getDayOfWeek(start, end))
      ratesForDay <- pure[E](rates.filter(_.days.contains(dayOfWeek)))
      rateForTime <- fromOption[E](
        getRateForTime(
          ratesForDay,
          start.toLocalTime,
          end.toLocalTime
        )
      )
    } yield rateForTime.price

    result.value
  }
}

object RateService {

  def validateDateRange(
    start: LocalDateTime,
    end: LocalDateTime
  ): Either[RateServiceError, Option[Unit]] =
    if (start.isAfter(end)) {
      // Ranges that end before they start are invalid
      Left(InvalidDateRangeError)
    } else if (Duration.between(start, end).compareTo(Duration.ofDays(1L)) > 0) {
      // Ranges that span days are valid, but not meaningful.
      Right(None)
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
