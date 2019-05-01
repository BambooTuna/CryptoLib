package com.github.BambooTuna.CryptoLib.restAPI.liquid

import com.github.BambooTuna.CryptoLib.restAPI.model._
import com.github.BambooTuna.CryptoLib.restAPI.useCase.RestAPISupport
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.{HttpRequestElement, ResponseJson}
import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpRequest, ResponseEntity}
import akka.http.scaladsl.unmarshalling.FromEntityUnmarshaller
import akka.stream.ActorMaterializer
import pdi.jwt.{JwtAlgorithm, JwtCirce}
import io.circe.syntax._
import io.circe.generic.auto._

import scala.concurrent.{ExecutionContextExecutor, Future}

class RestAPI(val apiKey: ApiKey, implicit val httpRequestElement: HttpRequestElement) extends RestAPISupport {


  override def run[O <: ResponseJson](entity: Option[Entity], queryString: Option[String])(implicit unmarshaller: FromEntityUnmarshaller[O], system: ActorSystem, materializer: ActorMaterializer, executionContext: ExecutionContextExecutor): Future[Either[Protocol.ErrorResponseJson, O]] = {
    doRequest[O](send(entity, queryString))
  }


  override def send(entity: Option[Entity], queryString: Option[String]): HttpRequest = {
    getHttpRequest(entity, queryString, Header(createHeaderMap(apiKey, entity, queryString)))
  }

  private def createHeaderMap(apiKey: ApiKey, entity: Option[Entity], queryString: Option[String]): Map[String, String] = {
    Map(
      "X-Quoine-API-Version" -> "2",
      "X-Quoine-Auth" -> createSign(apiKey, entity, queryString)(httpRequestElement),
      "Content-Type" -> "application/json"
    )
  }

  override protected def createSign(apiKey: ApiKey, entity: Option[Entity], queryString: Option[String])(implicit httpRequestElement: HttpRequestElement): String = {
    case class AuthParameters(path: String, nonce: Long = System.currentTimeMillis, token_id: String)
    val text = AuthParameters(
      path = httpRequestElement.path + queryString.map("?" + _).getOrElse(""),
      token_id = apiKey.key
    ).asJson.noSpaces
    HMACSHA256(text, apiKey.secret)
  }

  override protected def HMACSHA256(text: String, sharedSecret: String): String = {
    val algo = JwtAlgorithm.HS256
    JwtCirce.encode(text, sharedSecret, algo)
  }

}
