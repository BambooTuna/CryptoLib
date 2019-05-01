package com.github.BambooTuna.CryptoLib.restAPI.useCase

import akka.actor.ActorSystem
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.FromEntityUnmarshaller
import akka.stream.ActorMaterializer
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.{HttpRequestElement, ResponseJson}
import com.github.BambooTuna.CryptoLib.restAPI.model._

import scala.concurrent.{ExecutionContextExecutor, Future}

trait GenerateHttpRequest {
  implicit protected val httpRequestElement: HttpRequestElement

  def run[O <: ResponseJson](entity: Option[Entity], queryString: Option[String] = None)(implicit unmarshaller: FromEntityUnmarshaller[O], system: ActorSystem, materializer: ActorMaterializer, executionContext: ExecutionContextExecutor): Future[Either[Protocol.ErrorResponseJson, O]]
  def send(entity: Option[Entity], queryString: Option[String]): HttpRequest

  protected def getHttpRequest(entity: Option[Entity], queryString: Option[String], header: Header)(implicit httpRequestElement: HttpRequestElement): HttpRequest = {
    HttpRequest(
      method = httpRequestElement.method,
      uri = createUri(httpRequestElement.endpoint, httpRequestElement.path, queryString),
      headers = createRawHeaderList(header),
      entity = createEntity(entity)
    )
  }

  private def createUri(endpoint: Endpoint, path: Path, queryString: Option[String]): Uri = {
    Uri.from(scheme = endpoint.scheme, host = endpoint.host, path = path.path, queryString = queryString)
  }

  private def createRawHeaderList(header: Header): List[RawHeader] = {
    header.headers.toList.map(h => RawHeader(h._1, h._2))
  }

  private def createEntity(entity: Option[Entity]): RequestEntity = {
    HttpEntity(ContentTypes.`application/json`, entity.map(_.convertToString()).getOrElse(""))
  }

}
