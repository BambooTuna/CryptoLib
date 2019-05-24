package com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.BitflyerRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

case class GetMyAsset(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends BitflyerRestAPI[GetMyAssetBody, GetMyAssetQueryParameters, GetMyAssetResponse]
case class GetMyAssetBody() extends EmptyEntityRequestJson
case class GetMyAssetQueryParameters() extends EmptyQueryParametersJson
case class GetMyAssetResponse(
                               collateral: BigDecimal,
                               open_position_pnl: BigDecimal,
                               require_collateral: BigDecimal,
                               keep_rate: BigDecimal
                             ) extends EmptyResponseJson
