package com.github.BambooTuna.CryptoLib.restAPI.client.APIInterface

import com.github.BambooTuna.CryptoLib.restAPI.model._
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._
import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, StatusCodes}
import akka.stream.ActorMaterializer

import scala.concurrent.{ExecutionContextExecutor, Future}
import io.circe._

case class EmptyRestAPI[I, P, O]() extends RestAPISupport[I, P, O] {

  override implicit val httpRequestElement = HttpRequestElement.empty()
  override implicit val apiKey: ApiKey = ApiKey("", "")

  override def run(entity: Option[Entity[I]], queryParameters: Option[QueryParameters[P]])(implicit encodeI: Encoder[I], encodeP: Encoder[P], decoderO: Decoder[O], system: ActorSystem, materializer: ActorMaterializer, executionContext: ExecutionContextExecutor): Future[Either[ErrorResponseJson, O]] = {
    Future.successful(Left(ErrorResponseJson(StatusCodes.NotFound, "This API is not defined")))
  }

  override def send(entity: Option[Entity[I]], queryParameters: Option[QueryParameters[P]])(implicit encodeI: Encoder[I], encodeP: Encoder[P]): HttpRequest = {
    HttpRequest()
  }

  protected override def createHeaderMap(implicit apiKey: ApiKey, entityString: String, queryParametersMap: Map[String, String]): Map[String, String] = Map.empty

  protected override def createSign(implicit apiKey: ApiKey, entityString: String, queryParametersMap: Map[String, String]): String = ""

}
