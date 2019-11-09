package com.github.BambooTuna.CryptoLib.restAPI.cats

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.github.BambooTuna.CryptoLib.restAPI.cats.BitflyerInterpreter._
import com.github.BambooTuna.CryptoLib.restAPI.cats.SettingProtocol._
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym._
import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList.BitflyerEnumDefinition._
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global
import org.slf4j.{Logger, LoggerFactory}

object Sample extends App {

  implicit val system: ActorSystem                        = ActorSystem()
  implicit val materializer: ActorMaterializer            = ActorMaterializer()

  val logger: Logger = LoggerFactory.getLogger(getClass)

  val apiSetting = APISetting(APIEndpoint("https", "api.bitflyer.com", 443), APIAuth("", ""), logger)

  val flow = order(OrderData(
    child_order_type = OrderType.Limit,
    side = Side.Buy,
    price = 888888,
    size = 0.01
  ))

  val task1 = flow.run(apiSetting).value
  val r =
    (for {
      s <- Task.pure(java.time.Instant.now().toEpochMilli)
      r <- task1
    } yield {
      r.map(a => {
        println(java.time.Instant.now().toEpochMilli - s)
        a
      })
    }).runToFuture
  r.onComplete(println)

  Thread.sleep(100000000)

}
