package com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList.BitflyerEnumDefinition._
import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.BitflyerRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

case class GetExecutionsImpl(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends BitflyerRestAPI[GetExecutionsBodyImpl, GetExecutionsQueryParametersImpl, List[GetExecutionsResponseImpl]]
case class GetExecutionsBodyImpl() extends EmptyEntityRequestJson
case class GetExecutionsQueryParametersImpl(
                                             product_code: String = "FX_BTC_JPY",
                                             count: String = "100",
                                             before: String = "",
                                             after: String = "",
                                             child_order_id: String = "",
                                             child_order_acceptance_id: String = ""
                                           ) extends EmptyQueryParametersJson
case class GetExecutionsResponseImpl(
                                      id: Long,
                                      child_order_id: String,
                                      side: Side,
                                      price: Long,
                                      size: BigDecimal,
                                      commission: BigDecimal,
                                      exec_date: String,
                                      child_order_acceptance_id: String
                                  ) extends EmptyResponseJson