package com.github.BambooTuna.CryptoLib.restAPI.client.liquid.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.APIList.LiquidEnumDefinition._
import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.LiquidRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

class AmendOpenPosition(val apiKey: ApiKey, implicit val httpRequestElement: HttpRequestElement) extends LiquidRestAPI[AmendOpenPositionBody, AmendOpenPositionQueryParameters, AmendOpenPositionResponse]
case class AmendOpenPositionRequestData(
                                         stop_loss: BigDecimal,
                                         take_profit: BigDecimal
                                       )
case class AmendOpenPositionBody(
                                  trade: AmendOpenPositionRequestData
                             ) extends EmptyEntityRequestJson

case class AmendOpenPositionQueryParameters(
                                          id: String
                                        ) extends EmptyQueryParametersJson
case class AmendOpenPositionResponse(
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
                                 ) extends EmptyResponseJson