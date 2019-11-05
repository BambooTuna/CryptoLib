package com.github.BambooTuna.CryptoLib.restAPI.cats

import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpMethod, HttpRequest, RequestEntity, Uri}

case class APIAuth(key: String, secret: String)

trait HttpSYM[F[_]] {

  def createPath: F[Uri]

  def createMethod: F[HttpMethod]

  def createHeader: F[List[RawHeader]]

  def createEntity: F[RequestEntity]

  def createRequest(): F[HttpRequest]

  def HMACSHA256(text: String, secret: String): F[String]

}
