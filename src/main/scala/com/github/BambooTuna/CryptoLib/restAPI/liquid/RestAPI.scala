package com.github.BambooTuna.CryptoLib.restAPI.liquid

import com.github.BambooTuna.CryptoLib.restAPI.model._
import com.github.BambooTuna.CryptoLib.restAPI.useCase.RestAPISupport
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.{ErrorResponseJson, HttpRequestElement, RequestJson, ResponseJson}

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpRequest
import akka.stream.ActorMaterializer
import scala.concurrent.{ExecutionContextExecutor, Future}

import io.circe._
import io.circe.syntax._
import io.circe.generic.auto._

import pdi.jwt.{JwtAlgorithm, JwtCirce}

class RestAPI(val apiKey: ApiKey, implicit val httpRequestElement: HttpRequestElement) extends RestAPISupport {

  override def run[I <: RequestJson, O <: ResponseJson](entity: Option[Entity[I]], queryString: Option[String])(implicit encodeI: Encoder[I], decoderO: Decoder[O], system: ActorSystem, materializer: ActorMaterializer, executionContext: ExecutionContextExecutor): Future[Either[ErrorResponseJson, O]] = {
    doRequest[O](send(entity, queryString))
  }


  override def send[I <: RequestJson](entity: Option[Entity[I]], queryString: Option[String])(implicit encode: Encoder[I]): HttpRequest = {
    getHttpRequest(entity, queryString, Header(createHeaderMap(apiKey, entity, queryString)))
  }

  private def createHeaderMap[I <: RequestJson](apiKey: ApiKey, entity: Option[Entity[I]], queryString: Option[String]): Map[String, String] = {
    Map(
      "X-Quoine-API-Version" -> "2",
      "X-Quoine-Auth" -> createSign(apiKey, entity, queryString),
      "Content-Type" -> "application/json"
    )
  }

  override protected def createSign[I <: RequestJson](apiKey: ApiKey, entity: Option[Entity[I]], queryString: Option[String])(implicit httpRequestElement: HttpRequestElement): String = {
    case class AuthParameters(path: String, nonce: Long = System.currentTimeMillis, token_id: String)
    val text = AuthParameters(
      path = httpRequestElement.path.convertToString(queryString),
      token_id = apiKey.key
    ).asJson.noSpaces
    println(text)
    HMACSHA256(text, apiKey.secret)
  }

  override protected def HMACSHA256(text: String, sharedSecret: String): String = {
    val algo = JwtAlgorithm.HS256
    JwtCirce.encode(text, sharedSecret, algo)
  }

}
