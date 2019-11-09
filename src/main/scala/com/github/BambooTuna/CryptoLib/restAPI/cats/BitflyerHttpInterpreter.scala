package com.github.BambooTuna.CryptoLib.restAPI.cats

import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpMethod, HttpRequest, RequestEntity, Uri}
import akka.stream.Materializer
import cats.data.EitherT
import com.github.BambooTuna.CryptoLib.restAPI.cats.ExceptionHandleProtocol._
import com.github.BambooTuna.CryptoLib.restAPI.cats.SettingProtocol.APISetting
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core._
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym._
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import monix.eval.Task

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

object BitflyerHttpInterpreter {
  import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core.Reader._
  import com.github.BambooTuna.CryptoLib.restAPI.cats.HttpInterpreter._
  type Bitflyer[A] = Reader[APISetting, Task, HttpSYMInternalException, A]

  implicit val BitflyerHttpSYMInterpreter = new BitflyerHttpSYM[Bitflyer] {

    override def request(path: Bitflyer[Uri], method: Bitflyer[HttpMethod], headers: Bitflyer[List[RawHeader]], entity: Bitflyer[RequestEntity])(implicit ec: ExecutionContext, materializer: Materializer): Bitflyer[HttpRequest] =
      for {
        setting <- ask[APISetting, Task, HttpSYMInternalException]
        p <- path
        m <- method
        h <- headers
        e <- entity
        originEntity <- pure(EitherT[Task, HttpSYMInternalException, String](
          Task.fromFuture(
            e.toStrict(1.seconds).map(_.data.utf8String)
          )
            .map(Right(_))
            .onErrorHandle(e => Left(FetchOriginEntityDataException(e.getMessage)))
        ))
        timestamp <- pure(EitherT.rightT[Task, HttpSYMInternalException](java.time.Instant.now().toEpochMilli.toString))
        text <- pure(EitherT.rightT[Task, HttpSYMInternalException](timestamp + m.value + p.path.toString() + p.rawQueryString.map("?" + _).getOrElse("") + originEntity))
        sign <- HMACSHA256(text, setting.apiAuth.secret)
        authHeader <- header(Map(
          "ACCESS-KEY" -> setting.apiAuth.key,
          "ACCESS-TIMESTAMP" -> timestamp,
          "ACCESS-SIGN" -> sign
        ))
      } yield {
        println(originEntity)
        println(text)
        println(authHeader)

        HttpRequest(
          method = m,
          uri = p,
          headers = authHeader ++ h,
          entity = e
        )
      }

    override def HMACSHA256(text: String, secret: String): Bitflyer[String] =
      for {
        _ <- ask[APISetting, Task, HttpSYMInternalException]
      } yield {
        val algorithm = "HMacSha256"
        val secretKey = new SecretKeySpec(secret.getBytes, algorithm)
        val mac = Mac.getInstance(algorithm)
        mac.init(secretKey)
        mac.doFinal(text.getBytes).foldLeft("")((l, r) => l + Integer.toString((r & 0xff) + 0x100, 16).substring(1))
      }

  }

  def request(path: Bitflyer[Uri], method: Bitflyer[HttpMethod], headers: Bitflyer[List[RawHeader]], entity: Bitflyer[RequestEntity])(implicit ec: ExecutionContext, materializer: Materializer, T: BitflyerHttpSYM[Bitflyer]): Bitflyer[HttpRequest] =
    T.request(path, method, headers, entity)

  def HMACSHA256(text: String, secret: String)(implicit T: BitflyerHttpSYM[Bitflyer]): Bitflyer[String] =
    T.HMACSHA256(text, secret)

}
