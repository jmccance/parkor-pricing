import Dependencies._

lazy val `parkor-pricing` =
  (project in file("."))
    .settings(
      version := "0.1-SNAPSHOT",

      libraryDependencies ++= Seq(
        Akka.`akka-stream`,
        Akka.`akka-slf4j`,
        AkkaHttp.`akka-http-xml`,
        AkkaHttp.`akka-http`,
        Circe.`circe-core`,
        Circe.`circe-magnolia-derivation`,
        Circe.`circe-parser`,
        Logback.`logback-core`,
        Logback.`logback-classic`,
        `logstash-logback-encoder`,
        `akka-http-circe`,
        `scala-xml`,
        scaldi,
        pureconfig,

        Akka.`akka-stream-testkit` % Test,
        AkkaHttp.`akka-http-testkit` % Test,
        utest % Test
      ),

      testFrameworks += new TestFramework("utest.runner.Framework")
    )
