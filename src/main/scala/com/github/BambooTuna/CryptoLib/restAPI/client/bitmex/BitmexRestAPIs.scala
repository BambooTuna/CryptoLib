package com.github.BambooTuna.CryptoLib.restAPI.client.bitmex

import akka.http.scaladsl.model.HttpMethods
import com.github.BambooTuna.CryptoLib.restAPI.client.APIInterface.RestAPIs
import com.github.BambooTuna.CryptoLib.restAPI.client.bitmex.APIList._
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.HttpRequestElement
import com.github.BambooTuna.CryptoLib.restAPI.model.{ApiKey, Endpoint}

class BitmexRestAPIs(val apiKey: ApiKey) extends RestAPIs {

  val endpoint = Endpoint(
    scheme = "https",
    host = "api.bitflyer.com"
  )

}