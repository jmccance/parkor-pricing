import Dependencies._

lazy val `parkor-pricing` =
  (project in file("."))
    .settings(
      libraryDependencies ++= Seq(
        Akka.`akka-stream`,
        AkkaHttp.`akka-http`,
        AkkaHttp.`akka-http-xml`,
        `akka-http-circe`,
        Circe.`circe-core`,
        Circe.`circe-magnolia-derivation`,

        Akka.`akka-stream-testkit` % Test,
        AkkaHttp.`akka-http-testkit` % Test,
        utest % Test
      ),

      testFrameworks += new TestFramework("utest.runner.Framework")
    )
