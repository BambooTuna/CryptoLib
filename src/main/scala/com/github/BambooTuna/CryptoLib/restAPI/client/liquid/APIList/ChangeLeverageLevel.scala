package com.github.BambooTuna.CryptoLib.restAPI.client.liquid.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.APIList.LiquidEnumDefinition._
import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.LiquidRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

case class ChangeLeverageLevel(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends LiquidRestAPI[ChangeLeverageLevelBody, ChangeLeverageLevelQueryParameters, ChangeLeverageLevelResponse]
case class LeverageLevel(
                          leverage_level: Int
                        )
case class ChangeLeverageLevelBody(
                                        trading_account: LeverageLevel
                              ) extends EmptyEntityRequestJson
case class ChangeLeverageLevelQueryParameters(
                                                   id: String
                                                 ) extends EmptyQueryParametersJson
case class ChangeLeverageLevelResponse(
                                            id: Long,
                                            leverage_level: Int,
                                            max_leverage_level: Int,
                                            pnl: BigDecimal,
                                            equity: BigDecimal,
                                            margin: BigDecimal,
                                            free_margin: BigDecimal,
                                            trader_id: BigDecimal,
                                            status: OrderStatus,
                                            product_code: String,
                                            currency_pair_code: String,
                                            position: BigDecimal,
                                            balance: BigDecimal,
                                            created_at: Long,
                                            updated_at: Long,
                                            pusher_channel: String,
                                            margin_percent: BigDecimal,
                                            product_id: Int,
                                            funding_currency: String
                                  ) extends EmptyResponseJson