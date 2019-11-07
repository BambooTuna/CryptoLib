package com.github.BambooTuna.CryptoLib.restAPI.cats

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethod, HttpRequest, HttpResponse, RequestEntity, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer

import io.circe._
import io.circe.syntax._

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

import monix.eval.Task

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

object HttpInterpreter {
  import com.github.BambooTuna.CryptoLib.restAPI.cats.Reader._
  type Bitflyer[A] = Reader[APISetting, A, Task]

  implicit val HttpSYMInterpreter = new HttpSYM[Bitflyer] {
    override def createPath(path: String, queryString: Option[String] = None): Bitflyer[Uri] =
      for {
        setting <- ask[APISetting, Task]
      } yield Uri.from(scheme = setting.endpoint.scheme, host = setting.endpoint.host, path = path, queryString = queryString)

    override def createMethod(method: HttpMethod): Bitflyer[HttpMethod] =
      pure(Task.pure(method))

    override def createHeader(headers: Map[String, String]): Bitflyer[List[RawHeader]] =
      pure(Task.pure(headers.toList.map(h => RawHeader(h._1, h._2))))

    override def createEntity[T](entity: T)(f: T => String): Bitflyer[RequestEntity] =
      pure(Task.pure(HttpEntity(ContentTypes.`application/json`, f(entity))))

    override def createRequest(path: Bitflyer[Uri], method: Bitflyer[HttpMethod], headers: Bitflyer[List[RawHeader]], entity: Bitflyer[RequestEntity])(implicit ec: ExecutionContext, materializer: Materializer): Bitflyer[HttpRequest] =
      for {
        setting <- ask[APISetting, Task]
        p <- path
        m <- method
        h <- headers
        e <- entity
        originEntity <- pure(Task.fromFuture(e.toStrict(1.seconds).map(_.data.utf8String)))
        timestamp <- pure(Task.pure(java.time.Instant.now().toEpochMilli.toString))
        text <- pure(Task.pure(timestamp + m.value + p.path.toString() + p.rawQueryString.map("?" + _).getOrElse("") + originEntity))
        sign <- HMACSHA256(text, setting.apiAuth.secret)
        authHeader <- createHeader(Map(
          "ACCESS-KEY" -> setting.apiAuth.key,
          "ACCESS-TIMESTAMP" -> timestamp,
          "ACCESS-SIGN" -> sign
        ))
      } yield {
        println("text: " + text)
        println(authHeader ++ h)
        HttpRequest(
          method = m,
          uri = p,
          headers = authHeader ++ h,
          entity = e
        )
      }

    override def runRequest[O <: APIResponse](request: HttpRequest)(implicit decoderO: Decoder[O], system: ActorSystem, mat: Materializer): Bitflyer[O] =
      pure(Task
        .deferFutureAction { implicit ec =>
          println("deferFutureAction")
          Http()
            .singleRequest(request)
            .flatMap(handleErrorResponse(_))
        })


    private def handleErrorResponse[O <: APIResponse](r: HttpResponse)(implicit decoderO: Decoder[O], ec: ExecutionContext, mat: Materializer): Future[O] = {
      val unmarshaled: Unmarshal[HttpResponse] = Unmarshal(r)

      val result =
        unmarshaled.to[String]
          .map(v => {
            println("R: " + v)
            v
          })
          .map(_.asJson.as[O].fold(f => Future.failed(new Exception(f.message)), v => Future.successful(v))).flatten
      if (r.status.isSuccess)
        result
      else
        Future.failed(new Exception(s"status is not Success: $r"))
    }

    override def HMACSHA256(text: String, secret: String): Bitflyer[String] =
      for {
        _ <- ask[APISetting, Task]
      } yield {
        val algorithm = "HMacSha256"
        val secretKey = new SecretKeySpec(secret.getBytes, algorithm)
        val mac = Mac.getInstance(algorithm)
        mac.init(secretKey)
        mac.doFinal(text.getBytes).foldLeft("")((l, r) => l + Integer.toString((r & 0xff) + 0x100, 16).substring(1))
      }
  }

  def createPath(path: String, queryString: Option[String] = None)(implicit T: HttpSYM[Bitflyer]): Bitflyer[Uri] =
    T.createPath(path, queryString)

  def createMethod(method: HttpMethod)(implicit T: HttpSYM[Bitflyer]): Bitflyer[HttpMethod] =
    T.createMethod(method)

  def createHeader(headers: Map[String, String])(implicit T: HttpSYM[Bitflyer]): Bitflyer[List[RawHeader]] =
    T.createHeader(headers)

  def createEntity[T](entity: T)(f: T => String)(implicit T: HttpSYM[Bitflyer]): Bitflyer[RequestEntity] =
    T.createEntity(entity)(f)

  def createRequest(path: Bitflyer[Uri], method: Bitflyer[HttpMethod], headers: Bitflyer[List[RawHeader]], entity: Bitflyer[RequestEntity])(implicit ec: ExecutionContext, materializer: Materializer, T: HttpSYM[Bitflyer]): Bitflyer[HttpRequest] =
    T.createRequest(path, method, headers, entity)

  def runRequest[O <: APIResponse](implicit decoderO: Decoder[O], system: ActorSystem, materializer: Materializer, T: HttpSYM[Bitflyer]): HttpRequest => Bitflyer[O] =
    request => T.runRequest[O](request)

  def HMACSHA256(text: String, secret: String)(implicit T: HttpSYM[Bitflyer]): Bitflyer[String] =
    T.HMACSHA256(text, secret)

}

