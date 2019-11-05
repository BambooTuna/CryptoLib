package com.github.BambooTuna.CryptoLib.restAPI.cats

import com.github.BambooTuna.CryptoLib.restAPI.cats.BitflyerInterpreter._
import monix.execution.Scheduler.Implicits.global

object Sample extends App {



  val flow =
    order(OrderData("1")) ~> (o => order(OrderData("2"))) ~> order(OrderData("3"))

  val r = flow.run(APIAuth("", "")).runToFuture

  Thread.sleep(100000000)

}
