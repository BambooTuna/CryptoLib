package com.github.BambooTuna.CryptoLib.restAPI.cats.interpreter.core

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import cats.data.EitherT
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core.SettingProtocol.APISetting
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core.ExceptionHandleProtocol._
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core.{HttpSYM, NotUse, Reader}
import io.circe.{Decoder, parser}
import monix.eval.Task

object HttpInterpreter {
  import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core.Reader._
  type HttpBase[A] = Reader[APISetting, Task, HttpSYMInternalException, A]

  implicit val HttpSYMInterpreter = new HttpSYM[HttpBase] {
    override def url(path: String, queryParameters: Map[String, String] = Map.empty): HttpBase[Uri] =
      for {
        setting <- ask[APISetting, Task, HttpSYMInternalException]
      } yield {
        val queryString =
          if (queryParameters.nonEmpty)
            Some(queryParameters.foldLeft("")((l, r) => if (r._2 == "") l else l + s"&${r._1}=${r._2}").replaceFirst("&", ""))
          else None
        Uri.from(scheme = setting.endpoint.scheme, host = setting.endpoint.host, path = path, queryString = queryString)
      }

    override def method(method: HttpMethod): HttpBase[HttpMethod] =
      pure(EitherT.rightT(method))

    override def header(headers: Map[String, String]): HttpBase[List[RawHeader]] =
      pure(EitherT.rightT(headers.toList.map(h => RawHeader(h._1, h._2))))

    override def entity[T](entity: T)(f: T => String): HttpBase[RequestEntity] =
      pure(EitherT.rightT(
        entity match {
          case _: NotUse => HttpEntity.empty(ContentTypes.NoContentType)
          case other => HttpEntity(ContentTypes.`application/json`, f(other))
        }
      ))

    override def runRequest[O](request: HttpRequest)(implicit decoderO: Decoder[O], system: ActorSystem, mat: Materializer): HttpBase[O] =
      pure(EitherT{
        Task
          .deferFutureAction { implicit ec =>
            val start = java.time.Instant.now().toEpochMilli
            Http().singleRequest(request)
              .flatMap(r => {
                println(ResponseInformation("", java.time.Instant.now().toEpochMilli - start))
                Unmarshal(r).to[String]
                  .map(u =>
                    parser.decode[O](if (u.nonEmpty) u else "{}")
                      .left.map(e =>
                      DecodeResponseBodyException(e.getMessage, r.status.intValue(), Some(u))
                    )
                  )
                  .recover {
                    case e: IllegalArgumentException => Left(UnmarshalToStringException(e.getMessage))
                  }
              })
              .recover {
                case e: IllegalArgumentException => Left(HttpSingleRequestException(e.getMessage))
              }
          }
      })
  }

  def url(path: String, queryParameters: Map[String, String] = Map.empty)(implicit T: HttpSYM[HttpBase]): HttpBase[Uri] =
    T.url(path, queryParameters)

  def method(method: HttpMethod)(implicit T: HttpSYM[HttpBase]): HttpBase[HttpMethod] =
    T.method(method)

  def header(headers: Map[String, String])(implicit T: HttpSYM[HttpBase]): HttpBase[List[RawHeader]] =
    T.header(headers)

  def entity[T](entity: T)(f: T => String)(implicit T: HttpSYM[HttpBase]): HttpBase[RequestEntity] =
    T.entity(entity)(f)

  def runRequest[O](implicit decoderO: Decoder[O], system: ActorSystem, materializer: Materializer, T: HttpSYM[HttpBase]): HttpRequest => HttpBase[O] =
    request => T.runRequest[O](request)

}
