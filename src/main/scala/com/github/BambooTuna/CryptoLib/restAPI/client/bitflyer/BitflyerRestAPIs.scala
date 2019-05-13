package com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer

import com.github.BambooTuna.CryptoLib.restAPI.model.{ApiKey, Endpoint, Path}
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.HttpRequestElement
import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList._
import akka.http.scaladsl.model.HttpMethods
import com.github.BambooTuna.CryptoLib.restAPI.client.APIInterface.RestAPIs

class BitflyerRestAPIs(val apiKey: ApiKey) extends RestAPIs {

  val endpoint = Endpoint(
    scheme = "https",
    host = "api.bitflyer.com"
  )

  override val simpleOrder = SimpleOrderImpl(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.POST,
      path = Path("/v1/me/sendchildorder")
    )
  )

  override val getExecutions = GetExecutionsImpl(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.GET,
      path = Path("/v1/me/getexecutions")
    )
  )
}