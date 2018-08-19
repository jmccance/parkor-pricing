import Dependencies._

lazy val `parkor-pricing` =
  (project in file("."))
    .settings(
      version := "0.1-SNAPSHOT",

      libraryDependencies ++= Seq(
        Akka.`akka-stream`,
        AkkaHttp.`akka-http-xml`,
        AkkaHttp.`akka-http`,
        Circe.`circe-core`,
        Circe.`circe-magnolia-derivation`,
        Circe.`circe-parser`,
        `akka-http-circe`,
        `scala-xml`,
        pureconfig,

        Akka.`akka-stream-testkit` % Test,
        AkkaHttp.`akka-http-testkit` % Test,
        utest % Test
      ),

      testFrameworks += new TestFramework("utest.runner.Framework")
    )
