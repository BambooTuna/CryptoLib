package com.github.BambooTuna.CryptoLib.restAPI.client.liquid.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.APIInterface.APIList._
import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.APIList.LiquidEnumDefinition._
import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.LiquidRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.HttpRequestElement

case class CancelOrderImpl(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends LiquidRestAPI[CancelOrderBodyImpl, CancelOrderQueryParametersImpl, CancelOrderResponseImpl]
case class CancelOrderBodyImpl() extends CancelOrderBody
case class CancelOrderQueryParametersImpl(
                                           id: String
                                         ) extends CancelOrderQueryParameters
case class CancelOrderResponseImpl(
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
                                    currency_pair_code: String
                                  ) extends CancelOrderResponse