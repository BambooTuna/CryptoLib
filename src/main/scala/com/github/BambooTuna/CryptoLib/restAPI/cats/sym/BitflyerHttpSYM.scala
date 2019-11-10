package com.github.BambooTuna.CryptoLib.restAPI.cats.sym

import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpMethod, HttpRequest, RequestEntity, Uri}
import akka.stream.Materializer

import scala.concurrent.ExecutionContext

trait BitflyerHttpSYM[F[_]] {

  def request(url: Uri, method: HttpMethod, headers: List[RawHeader], entity: RequestEntity)(implicit ec: ExecutionContext, materializer: Materializer): F[HttpRequest]

  def HMACSHA256(text: String, secret: String): String

}
