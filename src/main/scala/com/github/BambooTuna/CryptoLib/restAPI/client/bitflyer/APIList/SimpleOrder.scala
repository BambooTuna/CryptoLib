package com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList.BitflyerEnumDefinition.{OrderType, Side}
import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.BitflyerRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

case class SimpleOrder(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends BitflyerRestAPI[SimpleOrderBody, SimpleOrderQueryParameters, SimpleOrderResponse]
case class SimpleOrderBody(
                                product_code: String,
                                child_order_type: OrderType,
                                side: Side,
                                price: Long,
                                size: BigDecimal,
                                minute_to_expire: Long = 43200,
                                time_in_force: String = "GTC"
                              ) extends EmptyEntityRequestJson
case class SimpleOrderQueryParameters() extends EmptyQueryParametersJson
case class SimpleOrderResponse(
                                    child_order_acceptance_id: String
                                  ) extends EmptyResponseJson
