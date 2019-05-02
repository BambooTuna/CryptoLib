package com.github.BambooTuna.CryptoLib.restAPI.model

import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.{EmptyEntityRequestJson, RequestJson}

import io.circe._
import io.circe.syntax._

case class Entity[I <: EmptyEntityRequestJson](requestJson: I = new EmptyEntityRequestJson()) {

  def convertToString(implicit encodeI: Encoder[I]): String = {

    requestJson.asJson.noSpaces

  }

}
