package com.github.BambooTuna.CryptoLib.restAPI.cats

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core.SettingProtocol._
import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList.BitflyerEnumDefinition.OrderType.Limit
import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList.BitflyerEnumDefinition.Side.Buy
import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList._
import monix.execution.Scheduler.Implicits.global
import org.slf4j.{Logger, LoggerFactory}


object Sample extends App {

  import com.github.BambooTuna.CryptoLib.restAPI.cats.interpreter.bitflyer.BitflyerInterpreter._
  import com.github.BambooTuna.CryptoLib.restAPI.cats.interpreter.bitflyer.BitflyerExpansionInterpreter._
  import scala.concurrent.duration._

  implicit val system: ActorSystem                        = ActorSystem()
  implicit val materializer: ActorMaterializer            = ActorMaterializer()
  val logger: Logger = LoggerFactory.getLogger(getClass)

  val apiSetting = APISetting(APIEndpoint("https", "api.bitflyer.com", 443), APIAuth("key", "secret"), logger)

  val flow1 =
    order(SimpleOrderBody(child_order_type = Limit, side = Buy, price = 888888, size = 0.01)) ~>
      sleep[SimpleOrderResponse](3.second) ~>
      cancelOrder ~>
      sleep[CancelOrderResponse](3.second) ~>
      cancelAllOrder

  val task1 = flow1.run(apiSetting).value
  val result = task1.runToFuture

  result.onComplete(println)

  Thread.sleep(100000000)

}
