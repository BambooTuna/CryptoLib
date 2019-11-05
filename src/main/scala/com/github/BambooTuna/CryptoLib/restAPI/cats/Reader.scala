package com.github.BambooTuna.CryptoLib.restAPI.cats

import cats.Monad

case class Reader[E, A, F[_]: Monad](g: E => F[A]) {
  def apply(e: E): F[A] = g(e)
  def run: E => F[A] = apply
  def map[B](f: A => B): Reader[E, B, F] = Reader(e => Monad[F].map(g(e))(f))
  def flatMap[B](f: A => Reader[E, B, F]): Reader[E, B, F] = Reader(e => Monad[F].flatMap(g(e))(a => f(a)(e)))

  def ~>[B](f: A => Reader[E, B, F]): Reader[E, B, F] = flatMap(f)
  def ~>[B](f: Reader[E, B, F]): Reader[E, B, F] = ~>(_ => f)
}

object Reader {
  def pure[E, A, F[_]: Monad](a: F[A]): Reader[E, A, F] = Reader(e => a)
  def ask[E, F[_]: Monad]: Reader[E, E, F] = Reader(a => Monad[F].pure(a))
  def local[E, A, F[_]: Monad](f: E => E, c: Reader[E, A, F]): Reader[E, A, F] = Reader(e => c(f(e)))
  def reader[E, A, F[_]: Monad](f: E => F[A]): Reader[E, A, F] = Reader(f)
}
