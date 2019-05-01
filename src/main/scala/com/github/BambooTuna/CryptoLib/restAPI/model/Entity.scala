package com.github.BambooTuna.CryptoLib.restAPI.model

import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.RequestJson

import io.circe._
import io.circe.syntax._

case class Entity[I <: RequestJson](requestJson: I) {

  def convertToString(implicit encode: Encoder[I]): String = {

    requestJson.asJson.noSpaces

  }

}
