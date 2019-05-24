package com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList.BitflyerEnumDefinition._
import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.BitflyerRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

case class GetMyPositions(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends BitflyerRestAPI[GetMyPositionsBody, GetMyPositionsQueryParameters, List[GetMyPositionsResponse]]
case class GetMyPositionsBody() extends EmptyEntityRequestJson
case class GetMyPositionsQueryParameters(
                                          product_code: String = "FX_BTC_JPY"
                                        ) extends EmptyQueryParametersJson
case class GetMyPositionsResponse(
                                   product_code: String,
                                   side: Side,
                                   price: Long,
                                   size: BigDecimal,
                                   commission: BigDecimal,
                                   swap_point_accumulate: BigDecimal,
                                   require_collateral: BigDecimal,
                                   open_date: String,
                                   leverage: Int,
                                   pnl: BigDecimal,
                                   sfd: BigDecimal
                                  ) extends EmptyResponseJson