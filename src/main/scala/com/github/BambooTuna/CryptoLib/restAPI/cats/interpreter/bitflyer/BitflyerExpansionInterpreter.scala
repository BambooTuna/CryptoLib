package com.github.BambooTuna.CryptoLib.restAPI.cats.interpreter.bitflyer

import cats.data.EitherT
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.bitflyer.BitflyerExpansionSYM
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core.ExceptionHandleProtocol.HttpSYMInternalException
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core.Reader
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core.SettingProtocol.APISetting
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

}
