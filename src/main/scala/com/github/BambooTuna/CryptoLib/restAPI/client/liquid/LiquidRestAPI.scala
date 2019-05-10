package com.github.BambooTuna.CryptoLib.restAPI.liquid

import com.github.BambooTuna.CryptoLib.restAPI.model._
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._
import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpRequest
import akka.stream.ActorMaterializer
import com.github.BambooTuna.CryptoLib.restAPI.client.APIInterface.RestAPISupport

import scala.concurrent.{ExecutionContextExecutor, Future}
import io.circe._
import io.circe.syntax._
import io.circe.generic.auto._
import pdi.jwt.{JwtAlgorithm, JwtCirce}

trait RestAPI[I <: EmptyEntityRequestJson, P <: EmptyQueryParametersJson, O <: EmptyResponseJson] extends RestAPISupport[I, P, O] {

  override def run(entity: Option[Entity[I]], queryParameters: Option[QueryParameters[P]])(implicit encodeI: Encoder[I], encodeP: Encoder[P], decoderO: Decoder[O], system: ActorSystem, materializer: ActorMaterializer, executionContext: ExecutionContextExecutor): Future[Either[ErrorResponseJson, O]] = {
    doRequest(send(entity, queryParameters))
  }

  override def send(entity: Option[Entity[I]], queryParameters: Option[QueryParameters[P]])(implicit encodeI: Encoder[I], encodeP: Encoder[P]): HttpRequest = {
    implicit val entityString = entity.map(_.convertToString).getOrElse("")
    implicit val queryParametersMap = queryParameters.map(_.getMap).getOrElse(Map.empty)
    getHttpRequest(Header(createHeaderMap))
  }

  override def createHeaderMap(implicit apiKey: ApiKey, entityString: String, queryParametersMap: Map[String, String]): Map[String, String] = {
    Map(
      "X-Quoine-API-Version" -> "2",
      "X-Quoine-Auth" -> createSign,
      "Content-Type" -> "application/json"
    )
  }

  override def createSign(implicit apiKey: ApiKey, entityString: String, queryParametersMap: Map[String, String]): String = {
    case class AuthParameters(path: String, nonce: Long = System.currentTimeMillis, token_id: String)
    val text = AuthParameters(
      path = httpRequestElement.path.addQueryParameters(queryParametersMap),
      token_id = apiKey.key
    ).asJson.noSpaces
    HMACSHA256(text, apiKey.secret)
  }

  override protected def HMACSHA256(text: String, sharedSecret: String): String = {
    val algo = JwtAlgorithm.HS256
    JwtCirce.encode(text, sharedSecret, algo)
  }

}