package com.github.BambooTuna.CryptoLib.restAPI.useCase

import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.{ErrorResponseJson, HttpRequestElement, RequestJson, ResponseJson}
import com.github.BambooTuna.CryptoLib.restAPI.model.{ApiKey, Entity}

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, StatusCodes}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import scala.concurrent.{ExecutionContextExecutor, Future}

import io.circe._

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

trait RestAPISupport extends GenerateHttpRequest {

  val apiKey: ApiKey

  protected def doRequest[O <: ResponseJson](httpRequest: HttpRequest)(implicit decoderO: Decoder[O], system: ActorSystem, materializer: ActorMaterializer, executionContext: ExecutionContextExecutor): Future[Either[ErrorResponseJson, O]] = {
    for {
      httpResponse <- Http().singleRequest(httpRequest)
      bodyString <- Unmarshal(httpResponse.entity).to[String]
      r <- httpResponse.status match {
        case StatusCodes.OK => parser.decode[O](bodyString).fold(_ => Future.successful(Left(ErrorResponseJson(StatusCodes.OK, bodyString))), o => Future.successful(Right(o)))
        case s => Future.successful(Left(ErrorResponseJson(s, bodyString)))
      }
    } yield r
  }

  protected def createSign[I <: RequestJson](apiKey: ApiKey, entity: Option[Entity[I]], queryString: Option[String])(implicit httpRequestElement: HttpRequestElement): String

  protected def HMACSHA256(text: String, sharedSecret: String): String = {
    val algorithm = "HMacSha256"
    val secret = new SecretKeySpec(sharedSecret.getBytes, algorithm)
    val mac = Mac.getInstance(algorithm)
    mac.init(secret)
    mac.doFinal(text.getBytes).foldLeft("")((l, r) => l + Integer.toString((r & 0xff) + 0x100, 16).substring(1))
  }

}
