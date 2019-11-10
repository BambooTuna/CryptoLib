package com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core

import akka.actor.ActorSystem
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpMethod, HttpRequest, RequestEntity, Uri}
import akka.stream.Materializer
import io.circe.Decoder

trait HttpSYM[F[_]] {

  def url(path: String, queryParameters: Map[String, String] = Map.empty): F[Uri]

  def method(method: HttpMethod): F[HttpMethod]

  def header(headers: Map[String, String]): F[List[RawHeader]]

  def entity[T](entity: T)(f: T => String): F[RequestEntity]

  def runRequest[O](request: HttpRequest)(implicit decoderO: Decoder[O] ,system: ActorSystem, materializer: Materializer): F[O]

}
