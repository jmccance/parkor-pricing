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

  object Logback {
    val version = "1.2.3"

    val `logback-classic` = "ch.qos.logback" % "logback-classic" % "1.2.3"
    val `logback-core` = "ch.qos.logback" % "logback-core" % "1.2.3"
  }

  val `logstash-logback-encoder` = "net.logstash.logback" % "logstash-logback-encoder" % "5.2"

  val pureconfig = "com.github.pureconfig" %% "pureconfig" % "0.9.1"

  val `scala-xml` = "org.scala-lang.modules" %% "scala-xml" % "1.1.0"

  val scaldi = "org.scaldi" %% "scaldi" % "0.5.8"

  val utest = "com.lihaoyi" %% "utest" % "0.6.3"
}
