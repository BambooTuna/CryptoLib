package com.github.BambooTuna.CryptoLib.restAPI.client.liquid.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.APIList.LiquidEnumDefinition._
import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.LiquidRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

case class GetMyOrders(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends LiquidRestAPI[GetMyOrdersBody, GetMyOrdersQueryParameters, GetMyOrdersResponse]
case class GetMyOrdersBody() extends EmptyEntityRequestJson
case class GetMyOrdersQueryParameters(
                                           funding_currency: String = "",
                                           product_id: String = "",
                                           status: OrderStatus = OrderStatus.Live,
                                           with_details: String = ""
                                         ) extends EmptyQueryParametersJson
case class MyOrderData(
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
                        //source_exchange: String,
                        product_id: Int,
                        product_code: String,
                        funding_currency: String,
                        currency_pair_code: String,
                        order_fee: BigDecimal
                      )
case class GetMyOrdersResponse(
                                    models: List[MyOrderData],
                                    current_page: Int,
                                    total_pages: Int
                                  ) extends EmptyResponseJson