package com.github.BambooTuna.CryptoLib.restAPI.model

case class Path(path: String) {
  def convertToString(queryString: Option[String]): String = {
    path + queryString.map("?" + _).getOrElse("")
  }
}
