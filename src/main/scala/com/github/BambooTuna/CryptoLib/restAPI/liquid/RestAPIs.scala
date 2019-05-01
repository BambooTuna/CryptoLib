package com.github.BambooTuna.CryptoLib.restAPI.liquid

import com.github.BambooTuna.CryptoLib.restAPI.model.{ApiKey, Endpoint, Path}
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.HttpRequestElement

import akka.http.scaladsl.model.HttpMethods

class RestAPIs(apiKey: ApiKey) {

  val simpleOrder = new RestAPI(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = Endpoint(
        scheme = "https",
        host = "api.liquid.com"
      ),
      method = HttpMethods.POST,
      path = Path("/orders/")
    )
  )

}
