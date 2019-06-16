import com.github.BambooTuna.CryptoLib.restAPI.model.{ApiKey, Entity, QueryParameters}
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.github.BambooTuna.CryptoLib.restAPI.client.discord.APIList.WebhookBody
import com.github.BambooTuna.CryptoLib.restAPI.client.discord.DiscordRestAPIs
import com.github.BambooTuna.CryptoLib.restAPI.client.wordpress.APIList.GetArticlesBody
import com.github.BambooTuna.CryptoLib.restAPI.client.wordpress.APIList.WordPressEnumDefinition.PostingStatus
import com.github.BambooTuna.CryptoLib.restAPI.client.wordpress.WordPressRestAPIs

import scala.concurrent.ExecutionContextExecutor

//この行は必須
import io.circe.generic.auto._

object Main extends App {
  implicit val system: ActorSystem                        = ActorSystem()
  implicit val materializer: ActorMaterializer            = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

//  discord()
//  bf()
//  liquid()

  wp()

  def discord(): Unit = {
    //https://discordapp.com/api/webhooks/123456789/abcdefg
    val d = new DiscordRestAPIs(ApiKey("123456789", "abcdefg"))
    d.webhook.run(
      entity = Some(
        Entity(
          WebhookBody(
            username = "test",
            content = "test mes"
          )
        )
      )
    ).map(println)
  }

  def bf(): Unit = {
    import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.BitflyerRestAPIs
    import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList._
    import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList.BitflyerEnumDefinition._
    val b = new BitflyerRestAPIs(ApiKey("6NaDdP3kDg7i2ET3vtdihf", "neyr2kqKAiYMm3pFok3AXejebIEn9gt8rUNcUXo73NY="))
    b.simpleOrder.run(
      entity = Some(
        Entity(SimpleOrderBody(
          product_code = "FX_BTC_JPY",
          child_order_type = OrderType.Limit,
          side = Side.Buy,
          price = 480000,
          size = 1
        ))
      )
    ).map(println)

    b.getExecutions.run(
      queryParameters = Some(
        QueryParameters(GetExecutionsQueryParameters(
          count = 500.toString,
          after = 414472500.toString

        ))
      )
    ).map(v => {
      v.right.foreach(a => a.foreach(println))
    })
  }
  def liquid(): Unit = {
    import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.LiquidRestAPIs
    import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.APIList._
    import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.APIList.LiquidEnumDefinition._

    val i = new LiquidRestAPIs(ApiKey("key", "secret"))

    val orderData = SimpleOrderBody(
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
          Entity(AmendOpenOrderBody(AmendOpenOrderRequestData(
            quantity = 0.02,
            price = 488999
          )))
        ),
        queryParameters = Some(
          QueryParameters(AmendOpenOrderQueryParameters(order.map(_.id.toString).getOrElse("1234567890")))
        )
      )
      leverage <- i.changeLeverageLevel.run(
        entity = Some(
          Entity(ChangeLeverageLevelBody(LeverageLevel(
            leverage_level = 10
          )))
        ),
        queryParameters = Some(
          QueryParameters(ChangeLeverageLevelQueryParameters("1234567890"))
        )
      )
      getOrder <- i.getMyOrders.run(
        queryParameters = Some(
          QueryParameters(GetMyOrdersQueryParameters(
            product_id = "5",
            status = OrderStatus.Live,
            with_details = "1"
          ))
        )
      )
      cancel <- i.cancelOrder.run(
        queryParameters = Some(
          QueryParameters(CancelOrderQueryParameters(order.map(_.id.toString).getOrElse("1234567890")))
        )
      )
      position <- i.getMyPositions.run(
        queryParameters = Some(
          QueryParameters(GetMyPositionsQueryParameters(
            status = PositionStatus.Open
          ))
        )
      )
      closeAll <- i.closeAllOpenPositions.run(
        queryParameters = Some(
          QueryParameters(CloseAllOpenPositionsQueryParameters(
            side = Side.Buy
          ))
        )
      )
    } yield {
      order.fold(println, println)
      amend.fold(println, println)
      leverage.fold(println, println)
      getOrder.fold(println, println)
      cancel.fold(println, println)
      position.fold(println, println)
      closeAll.fold(println, println)
    }
  }

  def wp() = {
    val i = new WordPressRestAPIs(ApiKey.empty)("bambootuna.com")
    i.getArticles.run(
      entity = Some(
        Entity(GetArticlesBody(
          search = "",
          status = PostingStatus.Draft,
          per_page = 100L
        ))
      )
    ).onComplete(println)

  }
}