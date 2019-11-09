package com.github.BambooTuna.CryptoLib.restAPI.cats.sym

import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpMethod, HttpRequest, RequestEntity, Uri}
import akka.stream.Materializer

import scala.concurrent.ExecutionContext

trait BitflyerHttpSYM[F[_]] {

  def request(path: F[Uri], method: F[HttpMethod], headers: F[List[RawHeader]], entity: F[RequestEntity])(implicit ec: ExecutionContext, materializer: Materializer): F[HttpRequest]

  def HMACSHA256(text: String, secret: String): F[String]

}
