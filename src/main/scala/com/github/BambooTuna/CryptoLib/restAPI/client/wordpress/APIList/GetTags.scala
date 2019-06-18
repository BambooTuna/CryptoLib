package com.github.BambooTuna.CryptoLib.restAPI.client.wordpress.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.wordpress.WordPressRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

case class GetTags(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends WordPressRestAPI[GetTagsBody, GetTagsQueryParameters, List[GetTagsResponse]]

case class GetTagsBody() extends EmptyEntityRequestJson

case class GetTagsQueryParameters() extends EmptyQueryParametersJson

case class GetTagsResponse(
                                id: Long,
                                name: String
                              ) extends EmptyResponseJson