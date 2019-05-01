import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.github.BambooTuna.CryptoLib.restAPI.liquid.RestAPIs
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.{SimpleOrderBody, SimpleOrderResponse}
import com.github.BambooTuna.CryptoLib.restAPI.model.{ApiKey, Entity}

import scala.concurrent.ExecutionContextExecutor

import com.github.BambooTuna.CryptoLib.restAPI.model.CirceSupport._
import io.circe.generic.auto._


object Main {
  implicit val system: ActorSystem                        = ActorSystem()
  implicit val materializer: ActorMaterializer            = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  def main(args: Array[String]): Unit = {
    val i = new RestAPIs(ApiKey("aaa", "bbb"))

    val orderData = SimpleOrderBody(
      order_type = "limit",
      product_id = 5,
      side = "buy",
      quantity = "0.01",
      price = "540000"
    )

    i.simpleOrder.run[SimpleOrderResponse](Some(Entity(orderData))).map(println)
  }
}
