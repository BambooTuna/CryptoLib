package com.github.BambooTuna.CryptoLib.restAPI.client.APIInterface

import akka.actor.ActorSystem
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.RawHeader
import akka.stream.ActorMaterializer
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.HttpRequestElement
import com.github.BambooTuna.CryptoLib.restAPI.model._
import io.circe.{Decoder, Encoder}

import scala.concurrent.{ExecutionContextExecutor, Future}

trait GenerateHttpRequest[I, P, O] {

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
    val p = path.addQueryParameters(queryParametersMap).split("\\?")
    val queryString = p.applyOrElse[Int, String](1, _ => "")
    Uri.from(scheme = endpoint.scheme, host = endpoint.host, path = p(0), queryString = if (queryString.isEmpty) None else Some(queryString))
  }

  private def createRawHeaderList(header: Header): List[RawHeader] = {
    header.headers.toList.map(h => RawHeader(h._1, h._2))
  }

  private def createEntity(entityString: String): RequestEntity = {
    HttpEntity(ContentTypes.`application/json`, entityString)
  }

}
