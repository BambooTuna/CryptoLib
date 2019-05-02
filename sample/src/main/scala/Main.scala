import com.github.BambooTuna.CryptoLib.restAPI.liquid.RestAPIs
import com.github.BambooTuna.CryptoLib.restAPI.model.{ApiKey, Entity, QueryParameters}
import com.github.BambooTuna.CryptoLib.restAPI.liquid.APIList._
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import scala.concurrent.ExecutionContextExecutor

//この行は必須
import io.circe.generic.auto._

object Main {
  implicit val system: ActorSystem                        = ActorSystem()
  implicit val materializer: ActorMaterializer            = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  def main(args: Array[String]): Unit = {
    val i = new RestAPIs(ApiKey("key", "secret"))

    val orderData = SimpleOrderBody(
      order_type = "limit",
      product_id = 5,//BTC　レバ取引
      side = "buy",
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
          Entity(AmendOpenOrderBody(AmendOpenOrderRequestData(
            quantity = 0.02,
            price = 488999
          )))
        ),
        queryParameters = Some(
          QueryParameters(AmendOpenOrderQueryParameters(order.map(_.id.toString).getOrElse("1234567890")))
        )
      )
      cancel <- i.cancelOrder.run(
        queryParameters = Some(
          QueryParameters(CancelOrderQueryParametersRequest(order.map(_.id.toString).getOrElse("1234567890")))
        )
      )
    } yield {
      amend.map(println)
      cancel.map(println)
    }
  }
}