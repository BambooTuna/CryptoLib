package com.github.BambooTuna.CryptoLib.restAPI.cats

import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpMethod, HttpRequest, RequestEntity, Uri}
import akka.stream.Materializer
import cats.data.{EitherT, Kleisli}
import com.github.BambooTuna.CryptoLib.restAPI.cats.ExceptionHandleProtocol._
import com.github.BambooTuna.CryptoLib.restAPI.cats.SettingProtocol.APISetting
import com.github.BambooTuna.CryptoLib.restAPI.cats.sym._
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import monix.eval.Task

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

object BitflyerHttpInterpreter {
  import com.github.BambooTuna.CryptoLib.restAPI.cats.HttpInterpreter._

  type ET[O] = EitherT[Task, HttpSYMInternalException, O]
  type BitflyerHttpBase[O] = Kleisli[ET, APISetting, O]

  implicit val BitflyerHttpSYMInterpreter = new BitflyerHttpSYM[BitflyerHttpBase] {

    override def request(url: Uri, method: HttpMethod, headers: List[RawHeader], entity: RequestEntity)(implicit ec: ExecutionContext, materializer: Materializer): BitflyerHttpBase[HttpRequest] =
      Kleisli { setting: APISetting =>
        (for {
          originEntity <-
            EitherT(
              Task.fromFuture(
                entity.toStrict(1.seconds).map(_.data.utf8String)
              )
                .map(Right(_))
                .onErrorHandle(e => Left(FetchOriginEntityDataException(e.getMessage)))
            ): ET[String]
        } yield {

          val timestamp = java.time.Instant.now().toEpochMilli.toString
          val text = timestamp + method.value + url.path.toString() + url.rawQueryString.map("?" + _).getOrElse("") + originEntity
          val sign = HMACSHA256(text, setting.apiAuth.secret)
          val authHeader =
            header(Map(
              "ACCESS-KEY" -> setting.apiAuth.key,
              "ACCESS-TIMESTAMP" -> timestamp,
              "ACCESS-SIGN" -> sign
            ))
          println(originEntity)
          println(text)
          println(authHeader)
          HttpRequest(
            method = method,
            uri = url,
            headers = authHeader ++ headers,
            entity = entity
          )
        }): ET[HttpRequest]
      }

    override def HMACSHA256(text: String, secret: String): String = {
      val algorithm = "HMacSha256"
      val secretKey = new SecretKeySpec(secret.getBytes, algorithm)
      val mac = Mac.getInstance(algorithm)
      mac.init(secretKey)
      mac.doFinal(text.getBytes).foldLeft("")((l, r) => l + Integer.toString((r & 0xff) + 0x100, 16).substring(1))
    }

  }

  def request(url: Uri, method: HttpMethod, headers: List[RawHeader], entity: RequestEntity)(implicit ec: ExecutionContext, materializer: Materializer, T: BitflyerHttpSYM[BitflyerHttpBase]): BitflyerHttpBase[HttpRequest] =
    T.request(url, method, headers, entity)

  def HMACSHA256(text: String, secret: String)(implicit T: BitflyerHttpSYM[BitflyerHttpBase]): String =
    T.HMACSHA256(text, secret)

}
