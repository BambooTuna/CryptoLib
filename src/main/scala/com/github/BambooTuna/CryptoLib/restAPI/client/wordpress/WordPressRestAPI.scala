package com.github.BambooTuna.CryptoLib.restAPI.client.wordpress

import java.io.{BufferedInputStream, File, InputStream}
import java.net.URL

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{ActorMaterializer, IOResult, scaladsl}
import akka.stream.scaladsl.{FileIO, Source}
import akka.util.ByteString
import com.github.BambooTuna.CryptoLib.restAPI.client.APIInterface.RestAPISupport
import com.github.BambooTuna.CryptoLib.restAPI.client.wordpress.APIList.MediaPostingBody
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._
import com.github.BambooTuna.CryptoLib.restAPI.model._
import io.circe._
import io.circe.generic.auto._
import wvlet.log.io.IOUtil

import scala.concurrent.{ExecutionContextExecutor, Future}

trait WordPressRestAPI[I, P, O] extends RestAPISupport[I, P, O] {

  override def run(entity: Option[Entity[I]], queryParameters: Option[QueryParameters[P]])(implicit encodeI: Encoder[I], encodeP: Encoder[P], decoderO: Decoder[O], system: ActorSystem, materializer: ActorMaterializer, executionContext: ExecutionContextExecutor): Future[Either[ErrorResponseJson, O]] = {
    doRequest(send(entity, queryParameters))
  }

  override def send(entity: Option[Entity[I]], queryParameters: Option[QueryParameters[P]])(implicit encodeI: Encoder[I], encodeP: Encoder[P]): HttpRequest = {
    implicit val entityString = entity.map(_.convertToString).getOrElse("")
    implicit val queryParametersMap = queryParameters.map(_.getMap).getOrElse(Map.empty)
    getHttpRequest(Header(createHeaderMap))
  }

  def uploadMedia(entity: Entity[MediaPostingBody], queryParameters: Option[QueryParameters[P]] = None)(implicit encodeP: Encoder[P], decoderO: Decoder[O], system: ActorSystem, materializer: ActorMaterializer, executionContext: ExecutionContextExecutor): Future[Either[ErrorResponseJson, O]] = {
    implicit val entityString = entity.convertToString
    implicit val queryParametersMap = queryParameters.map(_.getMap).getOrElse(Map.empty)
    val request = getHttpRequest(Header(createHeaderMap ++
      Map(
        "Content-Disposition" -> s"""attachment; filename=${entity.requestJson.fileName};""",
      )
    ))//.withEntity(createFileUploadEntity(new File(entity.requestJson.filePath)))
    for {
      tmp <- Http().singleRequest(HttpRequest(uri = entity.requestJson.filePath)).map(_.entity.dataBytes)
      result <- doRequest(request.withEntity(HttpEntity(ContentTypes.`application/octet-stream`, tmp).withSizeLimit(1024 * 1024 * 10)))
    } yield result
    //doRequest(request)
  }

  def createFileUploadEntity(file: File): MessageEntity = {
    HttpEntity(ContentTypes.`application/octet-stream`, FileIO.fromPath(file.toPath)).withSizeLimit(1024 * 1024 * 10)// 10M
  }

  override def createHeaderMap(implicit apiKey: ApiKey, entityString: String, queryParametersMap: Map[String, String]): Map[String, String] = {
    Map(
      "Authorization" -> s"Basic $createSign",
    )
  }

  override def createSign(implicit apiKey: ApiKey, entityString: String, queryParametersMap: Map[String, String]): String = {
    import java.util.Base64
    import java.nio.charset.StandardCharsets
    Base64.getEncoder.encodeToString(s"${apiKey.key}:${apiKey.secret}".getBytes(StandardCharsets.UTF_8))
  }

  override   protected def doRequest(httpRequest: HttpRequest)(implicit decoderO: Decoder[O], system: ActorSystem, materializer: ActorMaterializer, executionContext: ExecutionContextExecutor): Future[Either[ErrorResponseJson, O]] = {
    for {
      httpResponse <- Http().singleRequest(httpRequest)
      bodyString <- Unmarshal(httpResponse.entity).to[String]
      r <- httpResponse.status match {
        case StatusCodes.Created => parser.decode[O](bodyString).fold(_ => Future.successful(Left(ErrorResponseJson(StatusCodes.OK, bodyString))), o => Future.successful(Right(o)))
        case s => Future.successful(Left(ErrorResponseJson(s, bodyString)))
      }
    } yield r
  }

}
