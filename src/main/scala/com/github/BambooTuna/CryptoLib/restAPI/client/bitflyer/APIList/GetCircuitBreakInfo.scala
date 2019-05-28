package com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.BitflyerRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

case class GetCircuitBreakInfo(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends BitflyerRestAPI[GetCircuitBreakInfoBody, GetCircuitBreakInfoQueryParameters, GetCircuitBreakInfoResponse]
case class GetCircuitBreakInfoBody() extends EmptyEntityRequestJson
case class GetCircuitBreakInfoQueryParameters(
                                               productCode: String = "FX_BTC_JPY"
                                             ) extends EmptyQueryParametersJson

case class CircuitBreakInfoData(
                                 upper_limit: BigDecimal,
                                 lower_limit: BigDecimal
                               )
case class GetCircuitBreakInfoResponse(
                                data: CircuitBreakInfoData
                              ) extends EmptyResponseJson