package com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core

import cats.Monad
import cats.data.EitherT

case class Reader[E, F[_]: Monad, L, A](g: E => EitherT[F, L, A]) {
  def apply(e: E): EitherT[F, L, A] = g(e)
  def run: E => EitherT[F, L, A] = apply
  def map[B](f: A => B): Reader[E, F, L, B] = Reader(e => g(e).map(f))
  def flatMap[B](f: A => Reader[E, F, L, B]): Reader[E, F, L, B] = Reader(e => g(e).flatMap(a => f(a)(e)))

  def ~>[B](f: A => Reader[E, F, L, B]): Reader[E, F, L, B] = flatMap(f)
  def ~>[B](f: Reader[E, F, L, B]): Reader[E, F, L, B] = ~>(_ => f)
}

object Reader {
  def pure[E, F[_]: Monad, L, A](a: EitherT[F, L, A]): Reader[E, F, L, A] = Reader(e => a)
  def ask[E, F[_]: Monad, L]: Reader[E, F, L, E] = Reader(a => EitherT.pure(a))
  def local[E, F[_]: Monad, L, A](f: E => E, c: Reader[E, F, L, A]): Reader[E, F, L, A] = Reader(e => c(f(e)))
  def reader[E, F[_]: Monad, L, A](f: E => EitherT[F, L, A]): Reader[E, F, L, A] = Reader(f)
}
