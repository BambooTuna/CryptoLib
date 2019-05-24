package com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.BitflyerRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

case class CancelAllOrder(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends BitflyerRestAPI[CancelAllOrderBody, CancelAllOrderQueryParameters, CancelAllOrderResponse]
case class CancelAllOrderBody(
                            product_code: String = "FX_BTC_JPY"
                          ) extends EmptyEntityRequestJson
case class CancelAllOrderQueryParameters() extends EmptyQueryParametersJson
case class CancelAllOrderResponse() extends EmptyResponseJson
