import com.github.BambooTuna.CryptoLib.restAPI.model.{ApiKey, Entity, QueryParameters}


import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContextExecutor

//この行は必須
import io.circe.generic.auto._

object Main extends App {
  implicit val system: ActorSystem                        = ActorSystem()
  implicit val materializer: ActorMaterializer            = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  bf()
  liquid()

  def bf(): Unit = {
    import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.BitflyerRestAPIs
    import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList._
    import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList.BitflyerEnumDefinition._
    val b = new BitflyerRestAPIs(ApiKey("key", "secret"))
    b.simpleOrder.run(
      entity = Some(
        Entity(SimpleOrderBodyImpl(
          product_code = "FX_BTC_JPY",
          child_order_type = OrderType.Limit,
          side = Side.Buy,
          price = 480000,
          size = 1
        ))
      )
    ).map(println)
  }
  def liquid(): Unit = {
    import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.LiquidRestAPIs
    import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.APIList._
    import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.APIList.LiquidEnumDefinition._

    val i = new LiquidRestAPIs(ApiKey("key", "secret"))

    val orderData = SimpleOrderBodyImpl(
      order_type = OrderType.Limit,
      product_id = 5,//BTC　レバ取引
      side = Side.Buy,
      quantity = "0.01",
      price = "540000"
    )

    val result = for {
      order <- i.simpleOrder.run(
        entity = Some(
          Entity(orderData)
        )
      )
      amend <- i.amendOpenOrder.run(
        entity = Some(
          Entity(AmendOpenOrderBodyImpl(AmendOpenOrderRequestData(
            quantity = 0.02,
            price = 488999
          )))
        ),
        queryParameters = Some(
          QueryParameters(AmendOpenOrderQueryParametersImpl(order.map(_.id.toString).getOrElse("1234567890")))
        )
      )
      cancel <- i.cancelOrder.run(
        queryParameters = Some(
          QueryParameters(CancelOrderQueryParametersImpl(order.map(_.id.toString).getOrElse("1234567890")))
        )
      )
    } yield {
      amend.map(println)
      cancel.map(println)
    }
  }
}