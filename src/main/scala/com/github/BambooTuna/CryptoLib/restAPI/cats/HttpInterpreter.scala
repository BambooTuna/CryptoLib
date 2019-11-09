package com.github.BambooTuna.CryptoLib.restAPI.cats

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethod, HttpRequest, RequestEntity, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import cats.data.EitherT
import com.github.BambooTuna.CryptoLib.restAPI.cats.ExceptionHandleProtocol._
import com.github.BambooTuna.CryptoLib.restAPI.cats.SettingProtocol._
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core._
import io.circe._
import monix.eval.Task

object HttpInterpreter {
  import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core.Reader._
  type HttpBase[A] = Reader[APISetting, Task, HttpSYMInternalException, A]

  implicit val HttpSYMInterpreter = new HttpSYM[HttpBase] {
    override def url(path: String, queryString: Option[String] = None): HttpBase[Uri] =
      for {
        setting <- ask[APISetting, Task, HttpSYMInternalException]
      } yield Uri.from(scheme = setting.endpoint.scheme, host = setting.endpoint.host, path = path, queryString = queryString)

    override def method(method: HttpMethod): HttpBase[HttpMethod] =
      pure(EitherT.rightT(method))

    override def header(headers: Map[String, String]): HttpBase[List[RawHeader]] =
      pure(EitherT.rightT(headers.toList.map(h => RawHeader(h._1, h._2))))

    override def entity[T](entity: T)(f: T => String): HttpBase[RequestEntity] =
      pure(EitherT.rightT(HttpEntity(ContentTypes.`application/json`, f(entity))))



    override def runRequest[O <: APIResponse](request: HttpRequest)(implicit decoderO: Decoder[O], system: ActorSystem, mat: Materializer): HttpBase[O] =
      pure(EitherT{
        Task
          .deferFutureAction { implicit ec =>
            Http().singleRequest(request)
              .flatMap(r => {
                Unmarshal(r).to[String]
                  .map(u =>
                    parser.decode[O](u)
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

  def url(path: String, queryString: Option[String] = None)(implicit T: HttpSYM[HttpBase]): HttpBase[Uri] =
    T.url(path, queryString)

  def method(method: HttpMethod)(implicit T: HttpSYM[HttpBase]): HttpBase[HttpMethod] =
    T.method(method)

  def header(headers: Map[String, String])(implicit T: HttpSYM[HttpBase]): HttpBase[List[RawHeader]] =
    T.header(headers)

  def entity[T](entity: T)(f: T => String)(implicit T: HttpSYM[HttpBase]): HttpBase[RequestEntity] =
    T.entity(entity)(f)

  def runRequest[O <: APIResponse](implicit decoderO: Decoder[O], system: ActorSystem, materializer: Materializer, T: HttpSYM[HttpBase]): HttpRequest => HttpBase[O] =
    request => T.runRequest[O](request)

}

