package com.github.BambooTuna.CryptoLib.restAPI.useCase

import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.{HttpRequestElement, RequestJson, ResponseJson}
import com.github.BambooTuna.CryptoLib.restAPI.model._

import akka.actor.ActorSystem
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import scala.concurrent.{ExecutionContextExecutor, Future}

import io.circe._

trait GenerateHttpRequest {

  implicit protected val httpRequestElement: HttpRequestElement

  def run[I <: RequestJson, O <: ResponseJson](entity: Option[Entity[I]], queryString: Option[String] = None)(implicit encodeI: Encoder[I], decoderO: Decoder[O], system: ActorSystem, materializer: ActorMaterializer, executionContext: ExecutionContextExecutor): Future[Either[Protocol.ErrorResponseJson, O]]

  def send[I <: RequestJson](entity: Option[Entity[I]], queryString: Option[String])(implicit encode: Encoder[I]): HttpRequest

  protected def getHttpRequest[I <: RequestJson](entity: Option[Entity[I]], queryString: Option[String], header: Header)(implicit encode: Encoder[I], httpRequestElement: HttpRequestElement): HttpRequest = {
    HttpRequest(
      method = httpRequestElement.method,
      uri = createUri(httpRequestElement.endpoint, httpRequestElement.path, queryString),
      headers = createRawHeaderList(header),
      entity = createEntity[I](entity)
    )
  }

  private def createUri(endpoint: Endpoint, path: Path, queryString: Option[String]): Uri = {
    Uri.from(scheme = endpoint.scheme, host = endpoint.host, path = path.path, queryString = queryString)
  }

  private def createRawHeaderList(header: Header): List[RawHeader] = {
    header.headers.toList.map(h => RawHeader(h._1, h._2))
  }

  private def createEntity[I <: RequestJson](entity: Option[Entity[I]])(implicit encode: Encoder[I]): RequestEntity = {
    HttpEntity(ContentTypes.`application/json`, entity.map(_.convertToString).getOrElse(""))
  }

}
