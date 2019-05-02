package com.github.BambooTuna.CryptoLib.restAPI.liquid.APIList

import com.github.BambooTuna.CryptoLib.restAPI.liquid.RestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

class AmendOpenOrder(val apiKey: ApiKey, implicit val httpRequestElement: HttpRequestElement) extends RestAPI[AmendOpenOrderBody, AmendOpenOrderQueryParameters, AmendOpenOrderResponse]
case class AmendOpenOrderRequestData(
                                      quantity: BigDecimal,
                                      price: BigDecimal
                                    )
case class AmendOpenOrderBody(
                            order: AmendOpenOrderRequestData
                          ) extends EmptyEntityRequestJson

case class AmendOpenOrderQueryParameters(
                                        id: String
                                        ) extends EmptyQueryParametersJson
case class AmendOpenOrderResponse(
                                id: Long,
                                order_type: String,
                                quantity: BigDecimal,
                                disc_quantity: BigDecimal,
                                iceberg_total_quantity: BigDecimal,
                                side: String,
                                filled_quantity: BigDecimal,
                                price: BigDecimal,
                                created_at: Long,
                                updated_at: Long,
                                status: String,
                                leverage_level: Int,
                                source_exchange: String,
                                product_id: Int,
                                product_code: String,
                                funding_currency: String,
                                currency_pair_code: String,
                              ) extends EmptyResponseJson