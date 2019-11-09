package com.github.BambooTuna.CryptoLib.restAPI.cats

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpMethods
import akka.stream.Materializer
import com.github.BambooTuna.CryptoLib.restAPI.cats.ExceptionHandleProtocol.HttpSYMInternalException
import com.github.BambooTuna.CryptoLib.restAPI.cats.SettingProtocol.APISetting
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core._
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym._
import monix.eval.Task
import io.circe.syntax._
import io.circe.generic.auto._

import scala.concurrent.ExecutionContext

object BitflyerInterpreter {
  import com.github.BambooTuna.CryptoLib.restAPI.cats.HttpInterpreter._
  import com.github.BambooTuna.CryptoLib.restAPI.cats.BitflyerHttpInterpreter._
  type Bitflyer[A] = Reader[APISetting, Task, HttpSYMInternalException, A]

  implicit val BitflyerSYMInterpreter = new BitflyerSYM[Bitflyer] {
    def order(orderData: OrderData)(implicit system: ActorSystem, materializer: Materializer): Bitflyer[OrderId] =
      {
        implicit val ex: ExecutionContext = system.dispatcher
        request(
          url("/v1/me/sendchildorder", Some("aaa=aaa")),
          method(HttpMethods.POST),
          header(Map.empty),
          entity[OrderData](orderData)(_.asJson.noSpaces)
        ) ~> runRequest[OrderId]
      }

  }

  def order(request: OrderData)(implicit system: ActorSystem, materializer: Materializer, T: BitflyerSYM[Bitflyer]): Bitflyer[OrderId] =
    T.order(request)

}



