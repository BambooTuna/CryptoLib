package com.github.BambooTuna.CryptoLib.restAPI.useCase

import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._
import com.github.BambooTuna.CryptoLib.restAPI.model._
import akka.actor.ActorSystem
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer

import scala.concurrent.{ExecutionContextExecutor, Future}
import io.circe._

trait GenerateHttpRequest[I <: EmptyEntityRequestJson, P <: EmptyQueryParametersJson, O <: EmptyResponseJson] {

  implicit protected val httpRequestElement: HttpRequestElement

  def run(entity: Option[Entity[I]] = None, queryParameters: Option[QueryParameters[P]] = None)(implicit encodeI: Encoder[I], encodeP: Encoder[P], decoderO: Decoder[O], system: ActorSystem, materializer: ActorMaterializer, executionContext: ExecutionContextExecutor): Future[Either[Protocol.ErrorResponseJson, O]]

  def send(entity: Option[Entity[I]] = None, queryParameters: Option[QueryParameters[P]] = None)(implicit encodeI: Encoder[I], encodeP: Encoder[P]): HttpRequest

  protected def getHttpRequest(header: Header)(implicit entityString: String, queryParametersMap: Map[String, String]): HttpRequest = {
    HttpRequest(
      method = httpRequestElement.method,
      uri = createUri(httpRequestElement.endpoint, httpRequestElement.path),
      headers = createRawHeaderList(header),
      entity = createEntity(entityString)
    )
  }

  private def createUri(endpoint: Endpoint, path: Path)(implicit queryParametersMap: Map[String, String]): Uri = {
    Uri.from(scheme = endpoint.scheme, host = endpoint.host, path = path.addQueryParameters(queryParametersMap))
  }

  private def createRawHeaderList(header: Header): List[RawHeader] = {
    header.headers.toList.map(h => RawHeader(h._1, h._2))
  }

  private def createEntity(entityString: String): RequestEntity = {
    HttpEntity(ContentTypes.`application/json`, entityString)
  }

}
