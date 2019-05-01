package com.github.BambooTuna.CryptoLib.restAPI.model

case class Endpoint(scheme: String, host: String, path: Option[String] = None)
