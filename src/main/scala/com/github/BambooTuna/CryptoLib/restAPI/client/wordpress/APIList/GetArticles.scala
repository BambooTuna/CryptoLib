package com.github.BambooTuna.CryptoLib.restAPI.client.wordpress.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.wordpress.APIList.WordPressEnumDefinition.{BoolStatus, PostingStatus}
import com.github.BambooTuna.CryptoLib.restAPI.client.wordpress.WordPressRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

case class GetArticles(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends WordPressRestAPI[GetArticlesBody, GetArticlesQueryParameters, List[GetArticlesResponse]]

case class GetArticlesBody() extends EmptyEntityRequestJson

case class GetArticlesQueryParameters() extends EmptyQueryParametersJson

case class ContentData(rendered: String)
case class GetArticlesResponse(
                                   id: Long,
                                   date: String,
                                   content: ContentData,
                                   status: PostingStatus
                                 ) extends EmptyResponseJson
