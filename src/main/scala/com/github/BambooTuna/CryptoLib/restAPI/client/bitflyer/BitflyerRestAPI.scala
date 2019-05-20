package com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer

import com.github.BambooTuna.CryptoLib.restAPI.model._
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._
import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpRequest
import akka.stream.ActorMaterializer
import com.github.BambooTuna.CryptoLib.restAPI.client.APIInterface.RestAPISupport

import scala.concurrent.{ExecutionContextExecutor, Future}
import io.circe._

trait BitflyerRestAPI[I, P, O] extends RestAPISupport[I, P, O] {

  override def run(entity: Option[Entity[I]], queryParameters: Option[QueryParameters[P]])(implicit encodeI: Encoder[I], encodeP: Encoder[P], decoderO: Decoder[O], system: ActorSystem, materializer: ActorMaterializer, executionContext: ExecutionContextExecutor): Future[Either[ErrorResponseJson, O]] = {
    doRequest(send(entity, queryParameters))
  }

  override def send(entity: Option[Entity[I]], queryParameters: Option[QueryParameters[P]])(implicit encodeI: Encoder[I], encodeP: Encoder[P]): HttpRequest = {
    implicit val entityString = entity.map(_.convertToString).getOrElse("")
    implicit val queryParametersMap = queryParameters.map(_.getMap).getOrElse(Map.empty)
    getHttpRequest(Header(createHeaderMap))
  }

  override def createHeaderMap(implicit apiKey: ApiKey, entityString: String, queryParametersMap: Map[String, String]): Map[String, String] = {
    val timestamp = System.currentTimeMillis.toString
    Map(
      "ACCESS-KEY" -> apiKey.key,
      "ACCESS-TIMESTAMP" -> timestamp,
      "ACCESS-SIGN" -> HMACSHA256(timestamp + createSign, apiKey.secret)
    )
  }

  override def createSign(implicit apiKey: ApiKey, entityString: String, queryParametersMap: Map[String, String]): String = {
    httpRequestElement.method.value + httpRequestElement.path.addQueryParameters(queryParametersMap) + entityString
  }

}