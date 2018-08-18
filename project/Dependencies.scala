import sbt._

object Dependencies {

  object Akka {
    val version = "2.5.14"

    val `akka-stream` = "com.typesafe.akka" %% "akka-stream" % version
    val `akka-stream-testkit` = "com.typesafe.akka" %% "akka-stream-testkit" % version
  }

  object AkkaHttp {
    val version = "10.1.3"

    val `akka-http` = "com.typesafe.akka" %% "akka-http" % version
    val `akka-http-testkit` = "com.typesafe.akka" %% "akka-http-testkit" % version
  }

  val `akka-http-circe` = "de.heikoseeberger" %% "akka-http-circe" % "1.21.0"

  object Circe {
    val version = "0.9.3"

    val `circe-core` = "io.circe" %% "circe-core" % version
    val `circe-magnolia-derivation` = "io.circe" %% "circe-magnolia-derivation" % "0.2.0"
  }

  val utest = "com.lihaoyi" %% "utest" % "0.6.3"
}
