package com.github.BambooTuna.CryptoLib.restAPI.client.discord

import akka.http.scaladsl.model.HttpMethods
import com.github.BambooTuna.CryptoLib.restAPI.client.discord.APIList._
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.HttpRequestElement
import com.github.BambooTuna.CryptoLib.restAPI.model.{ApiKey, Endpoint, Path}

class DiscordRestAPIs(val apiKey: ApiKey) {

  val endpoint = Endpoint(
    scheme = "https",
    host = "discordapp.com"
  )

  val webhook = Webhook(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.POST,
      path = Path(s"/api/webhooks/${apiKey.key}/${apiKey.secret}")
    )
  )

}