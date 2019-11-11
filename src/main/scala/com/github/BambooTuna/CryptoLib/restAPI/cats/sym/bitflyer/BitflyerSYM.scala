package com.github.BambooTuna.CryptoLib.restAPI.cats.sym.bitflyer

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList._

trait BitflyerGETSYM[F[_]] {

  def getMyAsset(implicit system: ActorSystem, materializer: Materializer): F[GetMyAssetResponse]
  def getMyOrders(query: GetMyOrdersQueryParameters)(implicit system: ActorSystem, materializer: Materializer): F[Seq[GetMyOrdersResponse]]
  def getMyPositions(query: GetMyPositionsQueryParameters)(implicit system: ActorSystem, materializer: Materializer): F[Seq[GetMyPositionsResponse]]
  def getExecutions(query: GetExecutionsQueryParameters)(implicit system: ActorSystem, materializer: Materializer): F[Seq[GetExecutionsResponse]]
  def getCircuitBreakInfo(query: GetCircuitBreakInfoQueryParameters)(implicit system: ActorSystem, materializer: Materializer): F[GetCircuitBreakInfoResponse]

}

trait BitflyerPOSTSYM[F[_]] {

  def order(body: SimpleOrderBody)(implicit system: ActorSystem, materializer: Materializer): F[SimpleOrderResponse]
  def cancelOrder(body: CancelOrderBody)(implicit system: ActorSystem, materializer: Materializer): F[CancelOrderResponse]
  def cancelAllOrder(body: CancelAllOrderBody)(implicit system: ActorSystem, materializer: Materializer): F[CancelAllOrderResponse]

}
