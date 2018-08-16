import sbt._
import sbt.Keys._

object ProjectPlugin extends AutoPlugin {
  override val trigger = allRequirements

  override val projectSettings = Seq(
    organization := "parkor",
    scalaVersion := "2.12.6",

    // Options partially cribbed from https://tpolecat.github.io/2017/04/25/scalac-flags.html
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-unchecked",
      "-Xlint",
    )
  )
}
