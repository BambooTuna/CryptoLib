import sbt.Keys._
import sbt._

object Settings {
  lazy val commonSettings = Seq(
    organization := "com.github.BambooTuna",
    publishTo := Some(Resolver.file("CryptoLib",file("."))(Patterns(true, Resolver.mavenStyleBasePattern))),
    scalaVersion := "2.12.8",
    testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest,
                                          "-F",
                                          sys.env.getOrElse("SBT_TEST_TIME_FACTOR", "1")),
    libraryDependencies ++= Seq(
      ScalaTest.version       % Test,
      ScalaCheck.scalaCheck   % Test,
      ScalaMock.version       % Test,
      Circe.core,
      Circe.generic,
      Circe.parser,
      Akka.http,
      Akka.stream,
      Akka.slf4j,
      JWT.circe,
      Cats.version,
      Airframe.di,
      Enumeratum.version,
      Scala.java8Compat,
      Monix.version,
      Logback.classic,
      LogstashLogbackEncoder.encoder,
    )
  )

}
