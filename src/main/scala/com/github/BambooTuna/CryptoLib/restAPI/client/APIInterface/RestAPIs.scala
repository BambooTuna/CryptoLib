package com.github.BambooTuna.CryptoLib.restAPI.client.APIInterface

import com.github.BambooTuna.CryptoLib.restAPI.client.APIInterface.APIList._
import com.github.BambooTuna.CryptoLib.restAPI.model.{ApiKey, Endpoint}

trait RestAPIs {
  val apiKey: ApiKey
  val endpoint: Endpoint

  val simpleOrder: RestAPISupport[_ <: SimpleOrderBody, _ <: SimpleOrderQueryParameters, _ <: SimpleOrderResponse] = EmptyRestAPI[SimpleOrderBody, SimpleOrderQueryParameters, SimpleOrderResponse]()
  val amendOpenOrder: RestAPISupport[_ <: AmendOpenOrderBody, _ <: AmendOpenOrderQueryParameters, _ <: AmendOpenOrderResponse] = EmptyRestAPI[AmendOpenOrderBody, AmendOpenOrderQueryParameters, AmendOpenOrderResponse]()
  val amendOpenPosition: RestAPISupport[_ <: AmendOpenPositionBody, _ <: AmendOpenPositionQueryParameters, _ <: AmendOpenPositionResponse] = EmptyRestAPI[AmendOpenPositionBody, AmendOpenPositionQueryParameters, AmendOpenPositionResponse]()
  val cancelOrder: RestAPISupport[_ <: CancelOrderBody, _ <: CancelOrderQueryParameters, _ <: CancelOrderResponse] = EmptyRestAPI[CancelOrderBody, CancelOrderQueryParameters, CancelOrderResponse]()
  val changeLeverageLevel: RestAPISupport[_ <: ChangeLeverageLevelBody, _ <: ChangeLeverageLevelQueryParameters, _ <: ChangeLeverageLevelResponse] = EmptyRestAPI[ChangeLeverageLevelBody, ChangeLeverageLevelQueryParameters, ChangeLeverageLevelResponse]()
  val closeAllOpenPositions: RestAPISupport[_ <: CloseAllOpenPositionsBody, _ <: CloseAllOpenPositionsQueryParameters, _ <: CloseAllOpenPositionsResponse] = EmptyRestAPI[CloseAllOpenPositionsBody, CloseAllOpenPositionsQueryParameters, CloseAllOpenPositionsResponse]()
  val closeOpenPosition: RestAPISupport[_ <: CloseOpenPositionBody, _ <: CloseOpenPositionQueryParameters, _ <: CloseOpenPositionResponse] = EmptyRestAPI[CloseOpenPositionBody, CloseOpenPositionQueryParameters, CloseOpenPositionResponse]()
  val getMyOrders: RestAPISupport[_ <: GetMyOrdersBody, _ <: GetMyOrdersQueryParameters, _ <: GetMyOrdersResponse] = EmptyRestAPI[GetMyOrdersBody, GetMyOrdersQueryParameters, GetMyOrdersResponse]()
  val getMyPositions: RestAPISupport[_ <: GetMyPositionsBody, _ <: GetMyPositionsQueryParameters, _ <: GetMyPositionsResponse] = EmptyRestAPI[GetMyPositionsBody, GetMyPositionsQueryParameters, GetMyPositionsResponse]()

}
