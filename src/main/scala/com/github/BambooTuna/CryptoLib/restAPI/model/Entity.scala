package com.github.BambooTuna.CryptoLib.restAPI.model

import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.RequestJson

import io.circe._
import io.circe.syntax._
import io.circe.generic.auto._

case class Entity(requestJson: RequestJson) {
  def convertToString(): String = {
    requestJson.asJson.noSpaces
  }
}
