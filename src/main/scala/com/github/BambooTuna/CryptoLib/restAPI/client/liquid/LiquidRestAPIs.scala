package com.github.BambooTuna.CryptoLib.restAPI.client.liquid

import com.github.BambooTuna.CryptoLib.restAPI.model.{ApiKey, Endpoint, Path}
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.HttpRequestElement
import akka.http.scaladsl.model.HttpMethods
import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.APIList._

class LiquidRestAPIs(val apiKey: ApiKey) {

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

  val getMyOrders = new GetMyOrders(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.GET,
      path = Path("/orders")
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

  val getMyPositions = new GetMyPositions(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.GET,
      path = Path("/trades")
    )
  )

  val amendOpenPosition = new AmendOpenPosition(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.PUT,
      path = Path("/trades/:id")
    )
  )

  val closeOpenPosition = new CloseOpenPosition(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.PUT,
      path = Path("/trades/:id/close")
    )
  )

  val closeAllOpenPositions = new CloseAllOpenPositions(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.PUT,
      path = Path("/trades/close_all")
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