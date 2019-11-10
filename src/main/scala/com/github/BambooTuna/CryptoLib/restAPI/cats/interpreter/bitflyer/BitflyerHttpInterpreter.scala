package com.github.BambooTuna.CryptoLib.restAPI.cats.interpreter.bitflyer

import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpMethod, HttpRequest, RequestEntity, Uri}
import akka.stream.Materializer
import cats.data.EitherT
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.bitflyer.BitflyerHttpSYM
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core.ExceptionHandleProtocol.{FetchOriginEntityDataException, HttpSYMInternalException}
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core.Reader
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core.SettingProtocol.APISetting
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import monix.eval.Task

import scala.concurrent.ExecutionContext

import scala.concurrent.duration._

object BitflyerHttpInterpreter {
  import com.github.BambooTuna.CryptoLib.restAPI.cats.interpreter.core.HttpInterpreter._
  import com.github.BambooTuna.CryptoLib.restAPI.cats.sym.core.Reader._
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
            e.toStrict(10.millisecond).map(_.data.utf8String)
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
//        println(e)
//        println(originEntity)
//        println(text)
//        println(authHeader)

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
