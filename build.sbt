import Dependencies._

lazy val `parkor-pricing` =
  (project in file("."))
    .settings(
      libraryDependencies ++= Seq(
        Akka.`akka-stream-testkit` % Test,
        Akka.`akka-stream`,
        AkkaHttp.`akka-http-testkit` % Test,
        AkkaHttp.`akka-http`,
        `akka-http-circe`,
        Circe.`circe-core`,
        Circe.`circe-magnolia-derivation`
      )
    )
