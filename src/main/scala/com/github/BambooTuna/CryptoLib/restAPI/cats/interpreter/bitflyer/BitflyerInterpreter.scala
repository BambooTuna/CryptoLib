package com.github.BambooTuna.CryptoLib.restAPI.cats.interpreter.bitflyer

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpMethod, HttpMethods}
import akka.stream.Materializer
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.bitflyer.{BitflyerGETSYM, BitflyerPOSTSYM}
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core.ExceptionHandleProtocol.HttpSYMInternalException
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core.SettingProtocol.APISetting
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core.{NotUse, Reader}
import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList._

import io.circe._
import io.circe.syntax._
import io.circe.generic.auto._

import monix.eval.Task

import scala.concurrent.ExecutionContext

object BitflyerInterpreter {
  import com.github.BambooTuna.CryptoLib.restAPI.cats.interpreter.core.HttpInterpreter._
  import com.github.BambooTuna.CryptoLib.restAPI.cats.interpreter.bitflyer.BitflyerHttpInterpreter._
  type Bitflyer[A] = Reader[APISetting, Task, HttpSYMInternalException, A]

  implicit val BitflyerPOSTSYMInterpreter = new BitflyerPOSTSYM[Bitflyer] {
    override def order(body: SimpleOrderBody)(implicit system: ActorSystem, materializer: Materializer): Bitflyer[SimpleOrderResponse] =
      commonFlow[SimpleOrderBody, NotUse, SimpleOrderResponse]("/v1/me/sendchildorder", HttpMethods.POST)(body, NotUse.query)

    override def cancelOrder(body: CancelOrderBody)(implicit system: ActorSystem, materializer: Materializer): Bitflyer[CancelOrderResponse] =
      commonFlow[CancelOrderBody, NotUse, CancelOrderResponse]("/v1/me/cancelparentorder", HttpMethods.POST)(body, NotUse.query)

    override def cancelAllOrder(body: CancelAllOrderBody)(implicit system: ActorSystem, materializer: Materializer): Bitflyer[CancelAllOrderResponse] =
      commonFlow[CancelAllOrderBody, NotUse, CancelAllOrderResponse]("/v1/me/cancelallchildorders", HttpMethods.POST)(body, NotUse.query)

    private def commonFlow[B, Q, O](path: String, m: HttpMethod)(b: B, q: Q)(implicit system: ActorSystem, materializer: Materializer, encoderB: Encoder[B], encoderQ: Encoder[Q], decoderO: Decoder[O]): Bitflyer[O] =
      {
        implicit val ex: ExecutionContext = system.dispatcher
        request(
          url(path, q.asJson.as[Map[String, String]].getOrElse(Map.empty)),
          method(m),
          header(Map.empty),
          entity[B](b)(_.asJson.noSpaces)
        ) ~> runRequest[O]
      }
  }

  def order(request: SimpleOrderBody)(implicit system: ActorSystem, materializer: Materializer, T: BitflyerPOSTSYM[Bitflyer]) =
    T.order(request)

  def cancelOrder(request: CancelOrderBody)(implicit system: ActorSystem, materializer: Materializer, T: BitflyerPOSTSYM[Bitflyer]) =
    T.cancelOrder(request)

  def cancelAllOrder(request: CancelAllOrderBody)(implicit system: ActorSystem, materializer: Materializer, T: BitflyerPOSTSYM[Bitflyer]) =
    T.cancelAllOrder(request)

  implicit val BitflyerGETSYMInterpreter = new BitflyerGETSYM[Bitflyer] {

    override def getMyAsset(implicit system: ActorSystem, materializer: Materializer): Bitflyer[GetMyAssetResponse] =
      commonFlow[NotUse, NotUse, GetMyAssetResponse]("/v1/me/getbalance", HttpMethods.GET)(NotUse.body, NotUse.query)

    override def getMyOrders(query: GetMyOrdersQueryParameters)(implicit system: ActorSystem, materializer: Materializer): Bitflyer[Seq[GetMyOrdersResponse]] =
      commonFlow[NotUse, GetMyOrdersQueryParameters, Seq[GetMyOrdersResponse]]("/v1/me/getchildorders", HttpMethods.GET)(NotUse.body, query)

    override def getMyPositions(query: GetMyPositionsQueryParameters)(implicit system: ActorSystem, materializer: Materializer): Bitflyer[Seq[GetMyPositionsResponse]] =
      commonFlow[NotUse, GetMyPositionsQueryParameters, Seq[GetMyPositionsResponse]]("/v1/me/getpositions", HttpMethods.GET)(NotUse.body, query)

    override def getExecutions(query: GetExecutionsQueryParameters)(implicit system: ActorSystem, materializer: Materializer): Bitflyer[Seq[GetExecutionsResponse]] =
      commonFlow[NotUse, GetExecutionsQueryParameters, Seq[GetExecutionsResponse]]("/v1/me/getexecutions", HttpMethods.GET)(NotUse.body, query)

    override def getCircuitBreakInfo(query: GetCircuitBreakInfoQueryParameters)(implicit system: ActorSystem, materializer: Materializer): Bitflyer[GetCircuitBreakInfoResponse] =
      commonFlow[NotUse, GetCircuitBreakInfoQueryParameters, GetCircuitBreakInfoResponse]("/api/trade/getCircuitBreakInfo", HttpMethods.GET)(NotUse.body, query)

    private def commonFlow[B, Q, O](path: String, m: HttpMethod)(b: B, q: Q)(implicit system: ActorSystem, materializer: Materializer, encoderB: Encoder[B], encoderQ: Encoder[Q], decoderO: Decoder[O]): Bitflyer[O] =
    {
      implicit val ex: ExecutionContext = system.dispatcher
      request(
        url(path, q.asJson.as[Map[String, String]].getOrElse(Map.empty)),
        method(m),
        header(Map.empty),
        entity[B](b)(_.asJson.noSpaces)
      ) ~> runRequest[O]
    }

  }

  def getMyAsset(implicit system: ActorSystem, materializer: Materializer, T: BitflyerGETSYM[Bitflyer]) =
    T.getMyAsset

  def getMyOrders(request: GetMyOrdersQueryParameters)(implicit system: ActorSystem, materializer: Materializer, T: BitflyerGETSYM[Bitflyer]) =
    T.getMyOrders(request)

  def getMyPositions(request: GetMyPositionsQueryParameters)(implicit system: ActorSystem, materializer: Materializer, T: BitflyerGETSYM[Bitflyer]) =
    T.getMyPositions(request)

  def getExecutions(request: GetExecutionsQueryParameters)(implicit system: ActorSystem, materializer: Materializer, T: BitflyerGETSYM[Bitflyer]) =
    T.getExecutions(request)

  def getCircuitBreakInfo(request: GetCircuitBreakInfoQueryParameters)(implicit system: ActorSystem, materializer: Materializer, T: BitflyerGETSYM[Bitflyer]) =
    T.getCircuitBreakInfo(request)

}
