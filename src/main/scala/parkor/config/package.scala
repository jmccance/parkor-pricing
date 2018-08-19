package parkor

import java.io.File

import cats.data.NonEmptyList
import io.circe.parser._

import scala.io.Source

package object config {
  def readRateConfig(
    ratesFile: File,
    handleErrors: NonEmptyList[io.circe.Error] => RateConfig
  ): RateConfig = {
    val jsonText = Source.fromFile(ratesFile).mkString

    decodeAccumulating[RateConfig](jsonText).fold(handleErrors, identity)
  }
}
