package com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList.BitflyerEnumDefinition._
import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.BitflyerRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

case class GetMyOrders(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends BitflyerRestAPI[GetMyOrdersBody, GetMyOrdersQueryParameters, List[GetMyOrdersResponse]]
case class GetMyOrdersBody() extends EmptyEntityRequestJson
case class GetMyOrdersQueryParameters(
                                       product_code: String = "FX_BTC_JPY",
                                       count: String = "",
                                       before: String = "",
                                       after: String = "",
                                       child_order_state: String = OrderStatus.ACTIVE.value,
                                       child_order_id: String = "",
                                       child_order_acceptance_id: String = "",
                                       parent_order_id: String = ""
                                     ) extends EmptyQueryParametersJson
case class GetMyOrdersResponse(
                                id: Long,
                                child_order_id: String,
                                product_code: String,
                                side: Side,
                                child_order_type: OrderType,
                                price: Long,
                                average_price: BigDecimal,
                                size: BigDecimal,
                                child_order_state: OrderStatus,
                                expire_date: String,
                                child_order_date: String,
                                child_order_acceptance_id: String,
                                outstanding_size: BigDecimal,
                                cancel_size: BigDecimal,
                                executed_size: BigDecimal,
                                total_commission: BigDecimal
                              ) extends EmptyResponseJson
