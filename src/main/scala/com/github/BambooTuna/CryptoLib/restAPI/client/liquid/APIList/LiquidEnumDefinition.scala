package com.github.BambooTuna.CryptoLib.restAPI.client.liquid.APIList

import enumeratum.values._

object LiquidEnumDefinition {

  sealed abstract class OrderType(val value: String) extends StringEnumEntry
  case object OrderType extends StringEnum[OrderType] with StringCirceEnum[OrderType] {

    case object Limit  extends OrderType("limit")
    case object Market extends OrderType("market")
    case object MarketWithRange  extends OrderType("market_with_range")

    val values = findValues
  }

  sealed abstract class OrderDirection(val value: String) extends StringEnumEntry
  case object OrderDirection extends StringEnum[OrderDirection] with StringCirceEnum[OrderDirection] {

    case object OneDirection  extends OrderDirection("one_direction")
    case object TwoDirection extends OrderDirection("two_direction")
    case object Netout  extends OrderDirection("netout")

    val values = findValues
  }

  sealed abstract class Side(val value: String) extends StringEnumEntry
  case object Side extends StringEnum[Side] with StringCirceEnum[Side] {

    case object Buy  extends Side("buy")
    case object Sell extends Side("sell")
    case object Long  extends Side("long")
    case object Short  extends Side("short")

    val values = findValues
  }

  sealed abstract class OrderStatus(val value: String) extends StringEnumEntry
  case object OrderStatus extends StringEnum[OrderStatus] with StringCirceEnum[OrderStatus] {

    case object Live  extends OrderStatus("live")
    case object Filled  extends OrderStatus("filled")
    case object PartiallyFilled  extends OrderStatus("partially_filled")
    case object Cancelled extends OrderStatus("cancelled")
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
