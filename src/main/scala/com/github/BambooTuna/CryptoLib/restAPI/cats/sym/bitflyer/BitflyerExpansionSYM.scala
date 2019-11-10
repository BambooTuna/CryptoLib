package com.github.BambooTuna.CryptoLib.restAPI.cats.sym.bitflyer

import scala.concurrent.duration.FiniteDuration

trait BitflyerExpansionSYM[F[_]] {

  def sleep[I](period: FiniteDuration)(in: I): F[I]

}
