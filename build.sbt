import Settings._

lazy val sample = (project in file("sample"))
  .settings(commonSettings)
  .settings(
    name := "CryptoLib-sample",
    version := "1.0.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      Circe.core,
      Circe.generic,
      Circe.parser,
      Akka.http,
      Akka.stream,
      Akka.slf4j,
      JWT.circe
    )
  )
  .dependsOn(root)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    name := "CryptoLib",
    version := "1.0.3-SNAPSHOT",
    libraryDependencies ++= Seq(
      Circe.core,
      Circe.generic,
      Circe.parser,
      Akka.http,
      Akka.stream,
      Akka.slf4j,
      JWT.circe
    )
  )