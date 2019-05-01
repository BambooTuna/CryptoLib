package com.github.BambooTuna.CryptoLib.restAPI.model

import akka.http.scaladsl.model.{HttpMethod, StatusCode}

object Protocol {
  class RequestJson
  class ResponseJson




  case class ErrorResponseJson(statusCode: StatusCode, bodyString: String) extends ResponseJson


  case class HttpRequestElement(endpoint: Endpoint, method: HttpMethod, path: Path)

}
