package com.github.BambooTuna.CryptoLib.restAPI.client.line.APIList

import enumeratum.values._

object LineEnumDefinition {

  sealed abstract class MessageType(val value: String) extends StringEnumEntry
  case object MessageType extends StringEnum[MessageType] with StringCirceEnum[MessageType] {

    case object Text extends MessageType("text")

    val values = findValues
  }

}
