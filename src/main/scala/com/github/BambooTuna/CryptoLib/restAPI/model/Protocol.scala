package com.github.BambooTuna.CryptoLib.restAPI.model

import akka.http.scaladsl.model.{HttpMethod, StatusCode}

object Protocol {
  class RequestJson
  class ResponseJson


  case class SimpleOrderBody(
                              order_type: String,
                              product_id: Int,
                              side: String,
                              quantity: String,
                              price: String
                              //price_range: BigDecimal = 43200
                            ) extends RequestJson
  case class SimpleOrderResponse(
                                  id: Long,
                                  order_type: String,
                                  quantity: BigDecimal,
                                  disc_quantity: BigDecimal,
                                  iceberg_total_quantity: BigDecimal,
                                  side: String,
                                  filled_quantity: BigDecimal,
                                  price: BigDecimal,
                                  created_at: Long,
                                  updated_at: Long,
                                  status: String,
                                  leverage_level: Int,
                                  source_exchange: String,
                                  product_id: Int,
                                  product_code: String,
                                  funding_currency: String,
                                  currency_pair_code: String,
                                  order_fee: BigDecimal
                                ) extends ResponseJson

  case class ErrorResponseJson(statusCode: StatusCode, bodyString: String) extends ResponseJson


  case class HttpRequestElement(endpoint: Endpoint, method: HttpMethod, path: Path)

}
