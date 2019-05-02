package com.github.BambooTuna.CryptoLib.restAPI.liquid

import com.github.BambooTuna.CryptoLib.restAPI.model.{ApiKey, Endpoint, Path}
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.HttpRequestElement
import akka.http.scaladsl.model.HttpMethods
import com.github.BambooTuna.CryptoLib.restAPI.liquid.APIList._

class RestAPIs(apiKey: ApiKey) {
  val endpoint = Endpoint(
    scheme = "https",
    host = "api.liquid.com"
  )

  val simpleOrder = new SimpleOrder(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.POST,
      path = Path("/orders/")
    )
  )

  val cancelOrder = new CancelOrder(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.PUT,
      path = Path("/orders/:id/cancel")
    )
  )

  val amendOpenOrder = new AmendOpenOrder(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.PUT,
      path = Path("/orders/:id")
    )
  )

  val changeLeverageLevel = new ChangeLeverageLevel(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.PUT,
      path = Path("/trading_accounts/:id")
    )
  )

}