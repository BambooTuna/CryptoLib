import com.github.BambooTuna.CryptoLib.restAPI.liquid.Protocol.{SimpleOrderBody, SimpleOrderResponse}
import com.github.BambooTuna.CryptoLib.restAPI.liquid.RestAPIs
import com.github.BambooTuna.CryptoLib.restAPI.model.{ApiKey, Entity}

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
      product_id = 5,
      side = "buy",
      quantity = "0.01",
      price = "540000"
    )

    i.simpleOrder.run[SimpleOrderBody, SimpleOrderResponse](Some(Entity(orderData))).map(println)
  }
}
