package com.github.BambooTuna.CryptoLib.restAPI.client.wordpress.APIList

import enumeratum.values._

object WordPressEnumDefinition {
  sealed abstract class PostingStatus(val value: String) extends StringEnumEntry
  case object PostingStatus extends StringEnum[PostingStatus] with StringCirceEnum[PostingStatus] {

    case object Publish  extends PostingStatus("publish")
    case object Future  extends PostingStatus("future")
    case object Draft  extends PostingStatus("draft")
    case object Pending  extends PostingStatus("pending")
    case object Private  extends PostingStatus("private")

    val values = findValues
  }

  sealed abstract class BoolStatus(val value: String) extends StringEnumEntry
  case object BoolStatus extends StringEnum[BoolStatus] with StringCirceEnum[BoolStatus] {

    case object Open  extends BoolStatus("open")
    case object Close  extends BoolStatus("closed")

    val values = findValues
  }

  sealed abstract class MediaType(val value: String) extends StringEnumEntry
  case object MediaType extends StringEnum[MediaType] with StringCirceEnum[MediaType] {

    case object Image  extends MediaType("image")
    case object File  extends MediaType("file")

    val values = findValues
  }
}
