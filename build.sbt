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
        Jackson.`jackson-core`,     // For JSON logging
        Jackson.`jackson-databind`, // For JSON logging
        Log4j.`log4j-api`,
        Log4j.`log4j-core`,
        Log4j.`log4j-slf4j-impl`,
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
