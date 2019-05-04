package com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList.BitflyerEnumDefinition._
import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.BitflyerRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

class SimpleOrder(val apiKey: ApiKey, implicit val httpRequestElement: HttpRequestElement) extends BitflyerRestAPI[SimpleOrderEntity, EmptyQueryParametersJson, SimpleOrderResponse]

case class SimpleOrderEntity(
                              product_code: String,
                              child_order_type: OrderType,
                              side: Side,
                              price: Long,
                              size: BigDecimal,
                              minute_to_expire: Long = 43200,
                              time_in_force: String = "GTC"
                            ) extends EmptyEntityRequestJson
case class SimpleOrderResponse(
                                child_order_acceptance_id: String
                              ) extends EmptyResponseJson
