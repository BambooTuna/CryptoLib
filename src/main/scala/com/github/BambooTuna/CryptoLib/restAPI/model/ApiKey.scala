package com.github.BambooTuna.CryptoLib.restAPI.model

case class ApiKey(key: String, secret: String) {

}

object ApiKey {
  def empty = ApiKey("key", "secret")
}
