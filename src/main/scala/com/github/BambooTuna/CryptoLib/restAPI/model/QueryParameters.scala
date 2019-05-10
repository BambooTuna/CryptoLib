package com.github.BambooTuna.CryptoLib.restAPI.model

import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.EmptyQueryParametersJson

import io.circe._
import io.circe.syntax._

case class QueryParameters[P](parameters: P = new EmptyQueryParametersJson()) {

  def getMap(implicit encodeP: Encoder[P]): Map[String, String] = {

    parameters.asJson.as[Map[String, String]].getOrElse(Map.empty)

  }

}