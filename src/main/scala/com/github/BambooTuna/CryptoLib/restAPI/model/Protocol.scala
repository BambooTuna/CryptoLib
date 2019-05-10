package com.github.BambooTuna.CryptoLib.restAPI.model

import akka.http.scaladsl.model.{HttpMethod, HttpMethods, StatusCode}

object Protocol {

  sealed trait RequestJson
  sealed trait ResponseJson

  class EmptyEntityRequestJson() extends RequestJson
  class EmptyQueryParametersJson() extends RequestJson
  class EmptyResponseJson() extends ResponseJson

  case class ErrorResponseJson(statusCode: StatusCode, bodyString: String) extends EmptyResponseJson

  case class HttpRequestElement(endpoint: Endpoint, method: HttpMethod, path: Path)
  object HttpRequestElement {
    def empty() = HttpRequestElement(Endpoint("", ""), HttpMethods.POST, Path(""))
  }

}
