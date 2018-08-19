package parkor

import java.io.File

import parkor.domain.Rate

import io.circe.parser._

import scala.io.Source

package object config {
  def readRateConfig(ratesFile: File): Set[Rate] = {
    val jsonText = Source.fromFile(ratesFile).mkString

    val rates =
      for {
        rateConfig <- decode[RateConfig](jsonText).toSeq
        rateNode <- rateConfig.rates
      } yield rateNode.toRate

    rates.toSet
  }
}
