package com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList

import enumeratum.values._

object BitflyerEnumDefinition {

  sealed abstract class OrderType(val value: String) extends StringEnumEntry
  case object OrderType extends StringEnum[OrderType] with StringCirceEnum[OrderType] {

    case object Limit  extends OrderType("LIMIT")
    case object Market extends OrderType("MARKET")

    val values = findValues
  }

  sealed abstract class Side(val value: String) extends StringEnumEntry
  case object Side extends StringEnum[Side] with StringCirceEnum[Side] {

    case object Buy  extends Side("BUY")
    case object Sell extends Side("SELL")

    val values = findValues
  }

  sealed abstract class OrderStatus(val value: String) extends StringEnumEntry
  case object OrderStatus extends StringEnum[OrderStatus] with StringCirceEnum[OrderStatus] {

    case object ACTIVE  extends OrderStatus("ACTIVE")
    case object COMPLETED  extends OrderStatus("COMPLETED")
    case object CANCELED  extends OrderStatus("CANCELED")
    case object EXPIRED extends OrderStatus("EXPIRED")
    case object REJECTED extends OrderStatus("REJECTED")
    case object Empty extends OrderStatus("")

    val values = findValues
  }

  sealed abstract class PositionStatus(val value: String) extends StringEnumEntry
  case object PositionStatus extends StringEnum[PositionStatus] with StringCirceEnum[PositionStatus] {

    case object Open extends PositionStatus("open")
    case object Closed extends PositionStatus("closed")
    case object Empty extends PositionStatus("")

    val values = findValues
  }

}
