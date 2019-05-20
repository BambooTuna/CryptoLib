package com.github.BambooTuna.CryptoLib.restAPI.client.liquid.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.APIList.LiquidEnumDefinition._
import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.LiquidRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

case class SimpleOrder(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends LiquidRestAPI[SimpleOrderBody, SimpleOrderQueryParameters, SimpleOrderResponse]
case class SimpleOrderBody(
                                order_type: OrderType,
                                product_id: Int,
                                side: Side,
                                quantity: String,
                                price: String,
                                price_range: BigDecimal = 0,
                                leverage_level: Int = 25,
                                order_direction: OrderDirection = OrderDirection.Netout
                              ) extends EmptyEntityRequestJson
case class SimpleOrderQueryParameters() extends EmptyQueryParametersJson
case class SimpleOrderResponse(
                                    id: Long,
                                    order_type: OrderType,
                                    quantity: BigDecimal,
                                    disc_quantity: BigDecimal,
                                    iceberg_total_quantity: BigDecimal,
                                    side: Side,
                                    filled_quantity: BigDecimal,
                                    price: BigDecimal,
                                    created_at: Long,
                                    updated_at: Long,
                                    status: OrderStatus,
                                    leverage_level: Int,
                                    source_exchange: String,
                                    product_id: Int,
                                    product_code: String,
                                    funding_currency: String,
                                    currency_pair_code: String,
                                    order_fee: BigDecimal
                                  ) extends EmptyResponseJson
