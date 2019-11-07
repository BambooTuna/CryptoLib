package com.github.BambooTuna.CryptoLib.restAPI.cats

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList.BitflyerEnumDefinition.{OrderType, Side}

trait BitflyerSYM[F[_]] {
  def order(request: OrderData)(implicit system: ActorSystem, materializer: Materializer): F[OrderId]
}

case class OrderData(
                      product_code: String = "FX_BTC_JPY",
                      child_order_type: OrderType,
                      side: Side,
                      price: Long,
                      size: BigDecimal,
                      minute_to_expire: Long = 43200,
                      time_in_force: String = "GTC"
                    )
case class OrderId(id: String) extends APIResponse
case class AccountAsset(asset: String)
