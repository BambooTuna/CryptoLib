package com.github.BambooTuna.CryptoLib.restAPI.cats

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethod, HttpRequest, RequestEntity, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import cats.data.{EitherT, Kleisli}
import com.github.BambooTuna.CryptoLib.restAPI.cats.ExceptionHandleProtocol._
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core._
import io.circe._
import monix.eval.Task

object HttpInterpreter {
  type ET[O] = EitherT[Task, HttpSYMInternalException, O]
  type HttpBase[O] = Kleisli[ET, HttpRequest, O]

  implicit val HttpSYMInterpreter = new HttpSYM[HttpBase] {
    override def url(scheme: String, host: String, path: String, queryString: Option[String] = None): Uri =
      Uri.from(scheme = scheme, host = host, path = path, queryString = queryString)

    override def method(method: HttpMethod): HttpMethod =
      method

    override def header(headers: Map[String, String]): List[RawHeader] =
      headers.toList.map(h => RawHeader(h._1, h._2))

    override def entity[T](entity: T)(f: T => String): RequestEntity =
      HttpEntity(ContentTypes.`application/json`, f(entity))

    override def runRequest[O <: APIResponse](implicit decoderO: Decoder[O], system: ActorSystem, mat: Materializer): HttpBase[O] =
      Kleisli { request: HttpRequest =>
        EitherT{
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
        }: ET[O]
      }
  }

  def url(scheme: String, host: String, path: String, queryString: Option[String] = None)(implicit T: HttpSYM[HttpBase]): Uri =
    T.url(scheme, host, path, queryString)

  def method(method: HttpMethod)(implicit T: HttpSYM[HttpBase]): HttpMethod =
    T.method(method)

  def header(headers: Map[String, String])(implicit T: HttpSYM[HttpBase]): List[RawHeader] =
    T.header(headers)

  def entity[T](entity: T)(f: T => String)(implicit T: HttpSYM[HttpBase]): RequestEntity =
    T.entity(entity)(f)

  def runRequest[O <: APIResponse](implicit decoderO: Decoder[O], system: ActorSystem, materializer: Materializer, T: HttpSYM[HttpBase]): HttpBase[O] =
    T.runRequest[O]

}

