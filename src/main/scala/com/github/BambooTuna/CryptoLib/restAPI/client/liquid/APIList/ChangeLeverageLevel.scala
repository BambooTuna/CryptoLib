package com.github.BambooTuna.CryptoLib.restAPI.client.liquid.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.APIInterface.APIList._
import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.APIList.LiquidEnumDefinition._
import com.github.BambooTuna.CryptoLib.restAPI.client.liquid.LiquidRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.HttpRequestElement

case class ChangeLeverageLevelImpl(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends LiquidRestAPI[ChangeLeverageLevelBodyImpl, ChangeLeverageLevelQueryParametersImpl, ChangeLeverageLevelResponseImpl]
case class LeverageLevel(
                          leverage_level: Int
                        )
case class ChangeLeverageLevelBodyImpl(
                                        trading_account: LeverageLevel
                              ) extends ChangeLeverageLevelBody
case class ChangeLeverageLevelQueryParametersImpl(
                                                   id: String
                                                 ) extends ChangeLeverageLevelQueryParameters
case class ChangeLeverageLevelResponseImpl(
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
                                  ) extends ChangeLeverageLevelResponse