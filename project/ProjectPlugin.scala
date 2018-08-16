import sbt._
import sbt.Keys._

object ProjectPlugin extends AutoPlugin {
  override val trigger = allRequirements

  override val projectSettings = Seq(
    organization := "parkor",
    scalaVersion := "2.12.6"
  )
}
