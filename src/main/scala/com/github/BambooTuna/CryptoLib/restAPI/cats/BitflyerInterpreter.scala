package com.github.BambooTuna.CryptoLib.restAPI.cats

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpMethods
import akka.stream.Materializer
import monix.eval.Task
import io.circe.syntax._
import io.circe.generic.auto._

import scala.concurrent.ExecutionContext

object BitflyerInterpreter {
  import com.github.BambooTuna.CryptoLib.restAPI.cats.HttpInterpreter._
  type Bitflyer[A] = Reader[APISetting, A, Task]

  implicit val BitflyerSYMInterpreter = new BitflyerSYM[Bitflyer] {
    def order(request: OrderData)(implicit system: ActorSystem, materializer: Materializer): Bitflyer[OrderId] =
      {
        implicit val ex: ExecutionContext = system.dispatcher
        createRequest(
          createPath("/v1/me/sendchildorder"),
          createMethod(HttpMethods.POST),
          createHeader(Map.empty),
          createEntity[OrderData](request)(_.asJson.noSpaces)
        ) ~> runRequest[OrderId]
      }

  }

  def order(request: OrderData)(implicit system: ActorSystem, materializer: Materializer, T: BitflyerSYM[Bitflyer]): Bitflyer[OrderId] =
    T.order(request)

}



