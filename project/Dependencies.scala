import sbt._

object Scala {
  val java8Compat = "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0"
}

object Akka {
  private val version     = "2.5.19"
  val actor: ModuleID     = "com.typesafe.akka" %% "akka-actor" % version
  val stream: ModuleID    = "com.typesafe.akka" %% "akka-stream" % version
  val testkit: ModuleID   = "com.typesafe.akka" %% "akka-testkit" % version
  val slf4j: ModuleID     = "com.typesafe.akka" %% "akka-slf4j" % version
  private val httpVersion = "10.1.7"
  val http                = "com.typesafe.akka" %% "akka-http" % httpVersion
  val httpTestKit         = "com.typesafe.akka" %% "akka-http-testkit" % httpVersion
}

object ScalaMock {
  val version = "org.scalamock" %% "scalamock" % "4.1.0"
}

object Circe {
  private val version   = "0.11.1"
  val core: ModuleID    = "io.circe" %% "circe-core" % version
  val parser: ModuleID  = "io.circe" %% "circe-parser" % version
  val generic: ModuleID = "io.circe" %% "circe-generic" % version
  val extras: ModuleID  = "io.circe" %% "circe-generic-extras" % version

}

object Monix {
  val monixVersion = "3.0.0-RC2"
  val version      = "io.monix" %% "monix" % monixVersion
}

object Logback {
  private val version   = "1.2.3"
  val classic: ModuleID = "ch.qos.logback" % "logback-classic" % version
}

object LogstashLogbackEncoder {
  private val version = "4.11"
  val encoder = "net.logstash.logback" % "logstash-logback-encoder" % version excludeAll (
    ExclusionRule(organization = "com.fasterxml.jackson.core", name = "jackson-core"),
    ExclusionRule(organization = "com.fasterxml.jackson.core", name = "jackson-databind")
  )
}

object Janino {
  val version: ModuleID = "org.codehaus.janino" % "janino" % "2.7.8"
}

object ScalaLogging {
  val version      = "3.5.0"
  val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % version
}

object ScalaTest {
  val version = "org.scalatest" %% "scalatest" % "3.0.5"
}

object Cats {
  val version = "org.typelevel" %% "cats-core" % "1.5.0"
}

object Airframe {
  private val version = "0.80"
  val di              = "org.wvlet.airframe" %% "airframe" % version
}

object TypesafeConfig {
  val version = "com.typesafe" % "config" % "1.3.1"
}

object Shapeless {
  val version = "com.chuusai" %% "shapeless" % "2.3.3"
}

object Enumeratum {
  val version = "com.beachape" %% "enumeratum" % "1.5.12"
}

object Refined {
  val refined    = "eu.timepit" %% "refined" % "0.9.4"
  val cats       = "eu.timepit" %% "refined-cats" % "0.9.4"
  val eval       = "eu.timepit" %% "refined-eval" % "0.9.4"
  val jsonpath   = "eu.timepit" %% "refined-jsonpath" % "0.9.4"
  val pureconfig = "eu.timepit" %% "refined-pureconfig" % "0.9.4"
  val scalacheck = "eu.timepit" %% "refined-scalacheck" % "0.9.4"
  val all        = Seq(refined, cats, eval, jsonpath, pureconfig)
}

object ScalaCheck {
  val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.14.0"
}

object JWT {
  val circe = "com.pauldijou" %% "jwt-circe" % "2.1.0"
}