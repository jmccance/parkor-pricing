import sbt._

object Dependencies {

  object Akka {
    val version = "2.5.14"

    val `akka-slf4j` = "com.typesafe.akka" %% "akka-slf4j" % version
    val `akka-stream` = "com.typesafe.akka" %% "akka-stream" % version
    val `akka-stream-testkit` = "com.typesafe.akka" %% "akka-stream-testkit" % version
  }

  object AkkaHttp {
    val version = "10.1.3"

    val `akka-http` = "com.typesafe.akka" %% "akka-http" % version
    val `akka-http-testkit` = "com.typesafe.akka" %% "akka-http-testkit" % version
    val `akka-http-xml` = "com.typesafe.akka" %% "akka-http-xml" % version
  }

  val `akka-http-circe` = "de.heikoseeberger" %% "akka-http-circe" % "1.21.0"

  object Circe {
    val version = "0.9.3"

    val `circe-core` = "io.circe" %% "circe-core" % version
    val `circe-magnolia-derivation` = "io.circe" %% "circe-magnolia-derivation" % "0.2.0"
    val `circe-parser` = "io.circe" %% "circe-parser" % version
  }

  object Jackson {
    val version = "2.9.6"

    val `jackson-core` = "com.fasterxml.jackson.core" % "jackson-core" % version
    val `jackson-databind` = "com.fasterxml.jackson.core" % "jackson-databind" % version
  }

  object Log4j {
    val version = "2.11.1"

    val `log4j-api` = "org.apache.logging.log4j" % "log4j-api" % version
    val `log4j-core` = "org.apache.logging.log4j" % "log4j-core" % version
    val `log4j-slf4j-impl` = "org.apache.logging.log4j" % "log4j-slf4j-impl" % version
  }

  val pureconfig = "com.github.pureconfig" %% "pureconfig" % "0.9.1"

  val `scala-xml` = "org.scala-lang.modules" %% "scala-xml" % "1.1.0"

  val scaldi = "org.scaldi" %% "scaldi" % "0.5.8"

  val utest = "com.lihaoyi" %% "utest" % "0.6.3"
}
