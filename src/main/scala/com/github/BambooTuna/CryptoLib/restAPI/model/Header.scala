package com.github.BambooTuna.CryptoLib.restAPI.model

case class Header(headers: Map[String, String] = Map.empty) {

  def setHeader(header: Map[String, String]): Header = {
    copy(headers = headers ++ header)
  }

}
