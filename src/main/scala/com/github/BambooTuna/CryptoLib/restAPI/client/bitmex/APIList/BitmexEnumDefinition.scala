package com.github.BambooTuna.CryptoLib.restAPI.client.bitmex.APIList

import enumeratum.values._

object BitmexEnumDefinition {

  sealed abstract class OrderType(val value: String) extends StringEnumEntry
  case object OrderType extends StringEnum[OrderType] with StringCirceEnum[OrderType] {

    case object Limit  extends OrderType("Limit")

    val values = findValues
  }

  sealed abstract class Side(val value: String) extends StringEnumEntry
  case object Side extends StringEnum[Side] with StringCirceEnum[Side] {

    case object Buy  extends Side("Buy")
    case object Sell extends Side("Sell")

    val values = findValues
  }

  sealed abstract class ActionType(val value: String) extends StringEnumEntry
  case object ActionType extends StringEnum[ActionType] with StringCirceEnum[ActionType] {

    case object Partial  extends ActionType("partial")
    case object Insert  extends ActionType("insert")
    case object Update  extends ActionType("update")
    case object Delete  extends ActionType("delete")

    val values = findValues
  }

  sealed abstract class Symbol(val value: String) extends StringEnumEntry
  case object Symbol extends StringEnum[Symbol] with StringCirceEnum[Symbol] {
    case object XBTUSD extends Symbol("XBTUSD")
    case object ADAM19 extends Symbol("ADAM19")
    case object BCHM19 extends Symbol("BCHM19")
    case object EOSM19 extends Symbol("EOSM19")
    case object ETHUSD extends Symbol("ETHUSD")
    case object LTCM19 extends Symbol("LTCM19")
    case object TRXM19 extends Symbol("TRXM19")
    case object XBTM19 extends Symbol("XBTM19")

    val values = findValues
  }

}
