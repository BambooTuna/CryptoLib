package com.github.BambooTuna.CryptoLib.restAPI.cats

import org.slf4j.Logger

object SettingProtocol {

  case class APIAuth(key: String, secret: String)
  case class APIEndpoint(scheme: String, host: String, port: Int)

  case class APISetting(endpoint: APIEndpoint, apiAuth: APIAuth, logger: Logger)

}
