package com.github.BambooTuna.CryptoLib.restAPI.client.liquid.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.APIInterface.APIList._
import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.APIList.LiquidEnumDefinition._
import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.LiquidRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.HttpRequestElement

case class GetMyPositionsImpl(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends LiquidRestAPI[GetMyPositionsBodyImpl, GetMyPositionsQueryParametersImpl, GetMyPositionsResponseImpl]
case class GetMyPositionsBodyImpl() extends GetMyPositionsBody
case class GetMyPositionsQueryParametersImpl(
                                              funding_currency: String = "",
                                              status: PositionStatus = PositionStatus.Empty
                                            ) extends GetMyPositionsQueryParameters

case class MyPositionData(
                           id: Long,
                           currency_pair_code: String,
                           status: PositionStatus,
                           side: Side,
                           margin_used: BigDecimal,
                           open_quantity: BigDecimal,
                           close_quantity: BigDecimal,
                           quantity: BigDecimal,
                           leverage_level: Int,
                           product_code: String,
                           product_id: Int,
                           open_price: BigDecimal,
                           close_price: BigDecimal,
                           trader_id: Int,
                           open_pnl: BigDecimal,
                           close_pnl: BigDecimal,
                           pnl: BigDecimal,
                           stop_loss: BigDecimal,
                           take_profit: BigDecimal,
                           funding_currency: String,
                           created_at: Long,
                           updated_at: Long,
                           total_interest: BigDecimal
                         )
case class GetMyPositionsResponseImpl(
                                       models: List[MyPositionData],
                                       current_page: Int,
                                       total_pages: Int
                                  ) extends GetMyPositionsResponse