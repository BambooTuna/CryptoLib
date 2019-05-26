package com.github.BambooTuna.CryptoLib.restAPI.client.line

import akka.http.scaladsl.model.HttpMethods
import com.github.BambooTuna.CryptoLib.restAPI.client.line.APIList.SendMessage
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.HttpRequestElement
import com.github.BambooTuna.CryptoLib.restAPI.model.{ApiKey, Endpoint, Path}

class LineRestAPIs(val apiKey: ApiKey) {

  val endpoint = Endpoint(
    scheme = "https",
    host = "api.line.me"
  )

  val sendMessage = SendMessage(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.POST,
      path = Path("/v2/bot/message/push")
    )
  )

}
