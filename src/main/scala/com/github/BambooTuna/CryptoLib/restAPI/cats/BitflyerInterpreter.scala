package com.github.BambooTuna.CryptoLib.restAPI.cats

import monix.eval.Task

object BitflyerInterpreter {
  import com.github.BambooTuna.CryptoLib.restAPI.cats.Reader._
  import com.github.BambooTuna.CryptoLib.restAPI.cats.HttpInterpreter._
  type Bitflyer[A] = Reader[APIAuth, A, Task]

  implicit val BitflyerSYMInterpreter = new BitflyerSYM[Bitflyer] {
    def order(orderData: OrderData) =
      for {
        _ <- ask[APIAuth, Task]
        r <- pure(Task{
          println("order requested" + orderData)
          Thread.sleep(1000)
          println("order request end" + orderData)
          OrderId("order_id")
        })
      } yield r

    def getAsset =
      for {
        _ <- ask[APIAuth, Task]
        r <- pure(Task{AccountAsset("1234")})
      } yield r

    def cancelOrder(orderId: OrderId) =
      for {
        _ <- ask[APIAuth, Task]
        r <- pure(Task{true})
      } yield r

    private def getRequest[I, O](convert: String => O): Bitflyer[O] =
      for {
        _ <- HMACSHA256("", "")
      } yield convert("")
  }

  def order(orderData: OrderData)(implicit T: BitflyerSYM[Bitflyer]): Bitflyer[OrderId] =
    T.order(orderData)

  def getAsset(implicit T: BitflyerSYM[Bitflyer]): Bitflyer[AccountAsset] =
    T.getAsset

  def cancelOrder(orderId: OrderId)(implicit T: BitflyerSYM[Bitflyer]): Bitflyer[Boolean] =
    T.cancelOrder(orderId)

}



