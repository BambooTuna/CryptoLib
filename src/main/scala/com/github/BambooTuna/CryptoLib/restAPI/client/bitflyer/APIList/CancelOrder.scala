package com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.bitflyer.BitflyerRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

case class CancelOrder(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends BitflyerRestAPI[CancelOrderBody, CancelOrderQueryParameters, CancelOrderResponse]
case class CancelOrderBody(
                            product_code: String = "FX_BTC_JPY",
                            child_order_acceptance_id: String
                          ) extends EmptyEntityRequestJson
case class CancelOrderQueryParameters() extends EmptyQueryParametersJson
case class CancelOrderResponse() extends EmptyResponseJson
