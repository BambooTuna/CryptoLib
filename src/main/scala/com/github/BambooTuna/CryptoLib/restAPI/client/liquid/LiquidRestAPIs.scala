package com.github.BambooTuna.CryptoLib.restAPI.client.liquid

import com.github.BambooTuna.CryptoLib.restAPI.model.{ApiKey, Endpoint, Path}
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.HttpRequestElement
import akka.http.scaladsl.model.HttpMethods
import com.github.BambooTuna.CryptoLib.restAPI.client.APIInterface.RestAPIs
import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.APIList._

class LiquidRestAPIs(val apiKey: ApiKey) extends RestAPIs {

  val endpoint = Endpoint(
    scheme = "https",
    host = "api.liquid.com"
  )

  override val simpleOrder = SimpleOrderImpl(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.POST,
      path = Path("/orders/")
    )
  )

  override val cancelOrder = CancelOrderImpl(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.PUT,
      path = Path("/orders/:id/cancel")
    )
  )

  override val getMyOrders = GetMyOrdersImpl(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.GET,
      path = Path("/orders")
    )
  )

  override val amendOpenOrder = AmendOpenOrderImpl(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.PUT,
      path = Path("/orders/:id")
    )
  )

  override val getMyPositions = GetMyPositionsImpl(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.GET,
      path = Path("/trades")
    )
  )

  override val amendOpenPosition = AmendOpenPositionImpl(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.PUT,
      path = Path("/trades/:id")
    )
  )

  override val closeOpenPosition = CloseOpenPositionImpl(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.PUT,
      path = Path("/trades/:id/close")
    )
  )

  override val closeAllOpenPositions = CloseAllOpenPositionsImpl(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.PUT,
      path = Path("/trades/close_all")
    )
  )

  override val changeLeverageLevel = ChangeLeverageLevelImpl(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.PUT,
      path = Path("/trading_accounts/:id")
    )
  )

}