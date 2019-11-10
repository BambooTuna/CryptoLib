package com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core

import akka.actor.ActorSystem
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpMethod, HttpRequest, RequestEntity, Uri}
import akka.stream.Materializer
import io.circe.Decoder


trait APIResponse
case class APIRequestFailed(statusCode: Int, message: String, data: Option[String] = None) extends APIResponse

trait HttpSYM[F[_]] {

  def url(scheme: String, host: String, path: String, queryString: Option[String] = None): Uri

  def method(method: HttpMethod): HttpMethod

  def header(headers: Map[String, String]): List[RawHeader]

  def entity[T](entity: T)(f: T => String): RequestEntity

  def runRequest[O <: APIResponse](implicit decoderO: Decoder[O] ,system: ActorSystem, materializer: Materializer): F[O]

}
