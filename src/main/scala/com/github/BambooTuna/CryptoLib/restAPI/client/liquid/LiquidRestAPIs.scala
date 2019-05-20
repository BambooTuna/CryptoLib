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

  val simpleOrder = SimpleOrderImpl(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.POST,
      path = Path("/orders/")
    )
  )

  val cancelOrder = CancelOrderImpl(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.PUT,
      path = Path("/orders/:id/cancel")
    )
  )

  val getMyOrders = GetMyOrdersImpl(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.GET,
      path = Path("/orders")
    )
  )

  val amendOpenOrder = AmendOpenOrderImpl(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.PUT,
      path = Path("/orders/:id")
    )
  )

  val getMyPositions = GetMyPositionsImpl(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.GET,
      path = Path("/trades")
    )
  )

  val amendOpenPosition = AmendOpenPositionImpl(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.PUT,
      path = Path("/trades/:id")
    )
  )

  val closeOpenPosition = CloseOpenPositionImpl(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.PUT,
      path = Path("/trades/:id/close")
    )
  )

  val closeAllOpenPositions = CloseAllOpenPositionsImpl(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.PUT,
      path = Path("/trades/close_all")
    )
  )

  val changeLeverageLevel = ChangeLeverageLevelImpl(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.PUT,
      path = Path("/trading_accounts/:id")
    )
  )

}