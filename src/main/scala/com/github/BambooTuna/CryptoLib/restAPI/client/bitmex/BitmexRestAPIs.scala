package com.github.BambooTuna.CryptoLib.restAPI.client.bitmex

import com.github.BambooTuna.CryptoLib.restAPI.model.{ApiKey, Endpoint}

class BitmexRestAPIs(val apiKey: ApiKey) {

  val endpoint = Endpoint(
    scheme = "https",
    host = "api.bitflyer.com"
  )

}