package com.github.BambooTuna.CryptoLib.restAPI.liquid.APIList

import com.github.BambooTuna.CryptoLib.restAPI.liquid.APIList.EnumDefinition._
import com.github.BambooTuna.CryptoLib.restAPI.liquid.RestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

class ChangeLeverageLevel(val apiKey: ApiKey, implicit val httpRequestElement: HttpRequestElement) extends RestAPI[ChangeLeverageLevelBody, ChangeLeverageLevelQueryParameters, ChangeLeverageLevelResponse]
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
                                   status: String,
                                   product_code: String,
                                   currency_pair_code: String,
                                   position: BigDecimal,
                                   balance: BigDecimal,
                                   created_at: Long,
                                   updated_at: Long,
                                   pusher_channel: String,
                                   margin_percent: BigDecimal,
                                   product_id: Int,
                                   funding_currency: String,
                                 ) extends EmptyResponseJson
