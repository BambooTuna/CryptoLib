package com.github.BambooTuna.CryptoLib.restAPI.cats.interpreter.bitflyer

import akka.actor.ActorSystem
import akka.stream.Materializer
import cats.data.EitherT
import com.github.BambooTuna.CryptoLib.restAPI.cats.interpreter.bitflyer.BitflyerInterpreter.Bitflyer
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.bitflyer.{BitflyerExpansionSYM, BitflyerGETSYM, BitflyerPOSTSYM}
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core.ExceptionHandleProtocol.HttpSYMInternalException
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core.Reader
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core.SettingProtocol.APISetting
import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList.{CancelAllOrderBody, CancelAllOrderResponse, CancelOrderBody, CancelOrderResponse, GetMyOrdersQueryParameters, GetMyOrdersResponse, SimpleOrderResponse}
import monix.eval.Task

import scala.concurrent.duration.FiniteDuration

object BitflyerExpansionInterpreter {
  import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core.Reader._
  type Bitflyer[A] = Reader[APISetting, Task, HttpSYMInternalException, A]

  implicit val BitflyerExpansionSYMInterpreter = new BitflyerExpansionSYM[Bitflyer] {

    override def sleep[I](period: FiniteDuration)(in: I): Bitflyer[I] =
      pure(
        for {
          a <- EitherT.rightT(in): EitherT[Task, HttpSYMInternalException, I]
          r <- EitherT.right(Task(a).delayExecution(period)): EitherT[Task, HttpSYMInternalException, I]
        } yield r
      )


  }

  def sleep[I](period: FiniteDuration)(implicit T: BitflyerExpansionSYM[Bitflyer]): I => Bitflyer[I] =
    a => T.sleep(period)(a)

  def getMyOrders(implicit system: ActorSystem, materializer: Materializer, T: BitflyerGETSYM[Bitflyer]): Bitflyer[Seq[GetMyOrdersResponse]] =
    T.getMyOrders(GetMyOrdersQueryParameters())

  def cancelAllOrder(implicit system: ActorSystem, materializer: Materializer, T: BitflyerPOSTSYM[Bitflyer]): Bitflyer[CancelAllOrderResponse] =
    T.cancelAllOrder(CancelAllOrderBody())

  def cancelOrder(implicit system: ActorSystem, materializer: Materializer, T: BitflyerPOSTSYM[Bitflyer]): SimpleOrderResponse => Bitflyer[CancelOrderResponse] =
    request => T.cancelOrder(CancelOrderBody(child_order_acceptance_id = request.child_order_acceptance_id))

  def cancelOrder(request: SimpleOrderResponse)(implicit system: ActorSystem, materializer: Materializer, T: BitflyerPOSTSYM[Bitflyer]): Bitflyer[CancelOrderResponse] =
    T.cancelOrder(CancelOrderBody(child_order_acceptance_id = request.child_order_acceptance_id))

}
