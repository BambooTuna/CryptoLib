package com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer

import com.github.BambooTuna.CryptoLib.restAPI.model.{ApiKey, Endpoint, Path}
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.HttpRequestElement
import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList._
import akka.http.scaladsl.model.HttpMethods

class BitflyerRestAPIs(val apiKey: ApiKey) {

  val endpoint = Endpoint(
    scheme = "https",
    host = "api.bitflyer.com"
  )

  val simpleOrder = SimpleOrder(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.POST,
      path = Path("/v1/me/sendchildorder")
    )
  )

  val cancelOrder = CancelOrder(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.POST,
      path = Path("/v1/me/cancelparentorder")
    )
  )

  val cancelAllOrder = CancelAllOrder(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.POST,
      path = Path("/v1/me/cancelallchildorders")
    )
  )

  val getMyAsset = GetMyAsset(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.GET,
      path = Path("/v1/me/getbalance")
    )
  )

  val getMyOrders = GetMyOrders(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.GET,
      path = Path("/v1/me/getchildorders")
    )
  )

  val getMyPositions = GetMyPositions(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.GET,
      path = Path("/v1/me/getpositions")
    )
  )

  val getExecutions = GetExecutions(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.GET,
      path = Path("/v1/me/getexecutions")
    )
  )
}