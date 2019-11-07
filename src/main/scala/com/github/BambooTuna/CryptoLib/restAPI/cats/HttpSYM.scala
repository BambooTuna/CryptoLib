package com.github.BambooTuna.CryptoLib.restAPI.cats

import akka.actor.ActorSystem
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpMethod, HttpRequest, RequestEntity, Uri}
import akka.stream.Materializer
import io.circe.Decoder

import scala.concurrent.ExecutionContext

case class APIAuth(key: String, secret: String)
case class APIEndpoint(scheme: String, host: String, port: Int)
case class APISetting(endpoint: APIEndpoint, apiAuth: APIAuth)

trait APIResponse
case class APIRequestFailed(message: String) extends APIResponse

trait HttpSYM[F[_]] {

  def createPath(path: String, queryString: Option[String] = None): F[Uri]

  def createMethod(method: HttpMethod): F[HttpMethod]

  def createHeader(headers: Map[String, String]): F[List[RawHeader]]

  def createEntity[T](entity: T)(f: T => String): F[RequestEntity]

  def createRequest(path: F[Uri], method: F[HttpMethod], headers: F[List[RawHeader]], entity: F[RequestEntity])(implicit ec: ExecutionContext, materializer: Materializer): F[HttpRequest]

  def runRequest[O <: APIResponse](request: HttpRequest)(implicit decoderO: Decoder[O] ,system: ActorSystem, materializer: Materializer): F[O]

  def HMACSHA256(text: String, secret: String): F[String]

}
