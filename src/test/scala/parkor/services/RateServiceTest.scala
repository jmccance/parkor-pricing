package parkor.services

import java.time.DayOfWeek._
import java.time.{LocalDateTime, LocalTime}

import parkor.domain._
import utest._

object RateServiceTest extends TestSuite {
  val tests = Tests {
    "RateServiceImpl.rateFor" - {
      val rates =
        Set(
          Rate(
            Set(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY),
            start = LocalTime.parse("06:00"),
            end = LocalTime.parse("18:00"),
            price = Price(1500)
          ),
          Rate(
            Set(SATURDAY, SUNDAY),
            start = LocalTime.parse("06:00"),
            end = LocalTime.parse("20:00"),
            price = Price(2000)
          )
        )

      val rateService = new RateServiceImpl(rates)

      "Wednesday, 07:00-12:00" - {
        val start = LocalDateTime.parse("2015-07-01T07:00:00")
        val end = LocalDateTime.parse("2015-07-01T12:00:00")

        val result = rateService.priceFor(
          start,
          end
        )

        assert(result == Right(Some(Price(1500))))
      }

      "Saturday, 07:00-12:00" - {
        val start = LocalDateTime.parse("2015-07-04T07:00:00")
        val end = LocalDateTime.parse("2015-07-04T12:00:00")

        val result = rateService.priceFor(
          start,
          end
        )

        assert(result == Right(Some(Price(2000))))
      }

      "Saturday, 07:00-20:00" - {
        val start = LocalDateTime.parse("2015-07-04T07:00:00")
        val end = LocalDateTime.parse("2015-07-04T20:00:00")

        val result = rateService.priceFor(
          start,
          end
        )

        assert(result == Right(None))
      }
    }

    "getDayOfWeek" - {
      "returns the day of week if start and end are the same" - {
        val result = getDayOfWeek(
          LocalDateTime.parse("2018-08-18T12:45:21"),
          LocalDateTime.parse("2018-08-18T18:54:12")
        )

        assert(result == Right(SATURDAY))
      }

      "returns InvalidDateRangeError if start/end spans multiple days" - {
        val result = getDayOfWeek(
          LocalDateTime.parse("2018-08-18T12:45:21"),
          LocalDateTime.parse("2018-08-19T00:00:00")
        )

        assert(result == Left(RateServiceError.InvalidDateRangeError))
      }
    }
  }
}