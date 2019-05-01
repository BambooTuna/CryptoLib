package com.github.BambooTuna.CryptoLib.restAPI.useCase

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, StatusCodes}
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshal}
import akka.stream.ActorMaterializer
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.{ErrorResponseJson, HttpRequestElement, ResponseJson}
import com.github.BambooTuna.CryptoLib.restAPI.model.{ApiKey, Entity}
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

import scala.concurrent.{ExecutionContextExecutor, Future}

trait RestAPISupport extends GenerateHttpRequest {
  val apiKey: ApiKey

  protected def doRequest[A <: ResponseJson](httpRequest: HttpRequest)(implicit unmarshaller: FromEntityUnmarshaller[A], system: ActorSystem, materializer: ActorMaterializer, executionContext: ExecutionContextExecutor): Future[Either[ErrorResponseJson, A]] = {
    for {
      httpResponse <- Http().singleRequest(httpRequest)
      bodyString <- Unmarshal(httpResponse.entity).to[String]
      r <- httpResponse.status match {
        case StatusCodes.OK =>
          Unmarshal(httpResponse.entity).to[A].map(Right(_)).recoverWith{
            case _ => Future.successful(Left(ErrorResponseJson(StatusCodes.OK, bodyString)))
          }
        case s => Future.successful(Left(ErrorResponseJson(s, bodyString)))
      }
    } yield r
  }

  protected def createSign(apiKey: ApiKey, entity: Option[Entity], queryString: Option[String])(implicit httpRequestElement: HttpRequestElement): String

  protected def HMACSHA256(text: String, sharedSecret: String): String = {
    val algorithm = "HMacSha256"
    val secret = new SecretKeySpec(sharedSecret.getBytes, algorithm)
    val mac = Mac.getInstance(algorithm)
    mac.init(secret)
    mac.doFinal(text.getBytes).foldLeft("")((l, r) => l + Integer.toString((r & 0xff) + 0x100, 16).substring(1))
  }

}
