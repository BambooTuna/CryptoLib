package com.github.BambooTuna.CryptoLib.restAPI.cats

trait BitflyerSYM[F[_]] {
  def order(orderData: OrderData): F[OrderId]
  def getAsset: F[AccountAsset]
  def cancelOrder(orderId: OrderId): F[Boolean]
}

case class OrderData(id: String)
case class OrderId(id: String)
case class AccountAsset(asset: String)



