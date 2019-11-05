package com.github.BambooTuna.CryptoLib.restAPI.cats

import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpMethod, HttpRequest, RequestEntity, Uri}
import monix.eval.Task

object HttpInterpreter {
  import com.github.BambooTuna.CryptoLib.restAPI.cats.Reader._
  type Bitflyer[A] = Reader[APIAuth, A, Task]

  implicit val HttpSYMInterpreter = new HttpSYM[Bitflyer] {

    override def createPath: Bitflyer[Uri] = ???

    override def createMethod: Bitflyer[HttpMethod] = ???

    override def createHeader: Bitflyer[List[RawHeader]] = ???

    override def createEntity: Bitflyer[RequestEntity] = ???

    override def createRequest =
      for {
        p <- createPath
        m <- createMethod
        h <- createHeader
        e <- createEntity
        r <- pure(Task{
          HttpRequest(
            method = m,
            uri = p,
            headers = h,
            entity = e
          )
        })
      } yield r

    override def HMACSHA256(text: String, secret: String) =
      for {
        _ <- ask[APIAuth, Task]
        r <- pure(Task{""})
      } yield r
  }

  def createRequest(implicit T: HttpSYM[Bitflyer]): Bitflyer[HttpRequest] =
    T.createRequest()

  def HMACSHA256(text: String, secret: String)(implicit T: HttpSYM[Bitflyer]): Bitflyer[String] =
    T.HMACSHA256(text, secret)

}



