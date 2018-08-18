package parkor.domain

import java.time.{DayOfWeek, LocalTime}

case class Rate(
  days: Set[DayOfWeek],
  start: LocalTime,
  end: LocalTime,
  price: Price
)

case class Price(value: BigInt)

