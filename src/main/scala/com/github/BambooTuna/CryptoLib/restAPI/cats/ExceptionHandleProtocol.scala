package com.github.BambooTuna.CryptoLib.restAPI.cats

object ExceptionHandleProtocol {

  trait HttpSYMInternalException {
    val message: String
  }
  case class FetchOriginEntityDataException(message: String) extends HttpSYMInternalException
  case class DecodeResponseBodyException(message: String, statusCode: Int, data: Option[String] = None) extends HttpSYMInternalException

  case class HttpSingleRequestException(message: String) extends HttpSYMInternalException
  case class UnmarshalToStringException(message: String) extends HttpSYMInternalException

}
