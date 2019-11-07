package com.github.BambooTuna.CryptoLib.restAPI.cats

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.github.BambooTuna.CryptoLib.restAPI.cats.BitflyerInterpreter._
import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList.BitflyerEnumDefinition._
import monix.execution.Scheduler.Implicits.global

object Sample extends App {

  implicit val system: ActorSystem                        = ActorSystem()
  implicit val materializer: ActorMaterializer            = ActorMaterializer()

  val apiSetting = APISetting(APIEndpoint("https", "api.bitflyer.com", 443), APIAuth("", ""))

  val flow = order(OrderData(
    child_order_type = OrderType.Limit,
    side = Side.Buy,
    price = 888888,
    size = 0.01
  ))

  val r = flow.run(apiSetting).runToFuture

  r.onComplete(println)

  Thread.sleep(100000000)

}
