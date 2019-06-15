package com.github.BambooTuna.CryptoLib.restAPI.client.wordpress.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.wordpress.WordPressRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

case class MediaPosting(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends WordPressRestAPI[MediaPostingBody, MediaPostingQueryParameters, MediaPostingResponse]

case class MediaPostingBody(
                             fileName: String,
                             filePath: String
                           ) extends EmptyEntityRequestJson

case class MediaPostingQueryParameters() extends EmptyQueryParametersJson

case class MediaPostingResponse(
                                   id: Int
                                 ) extends EmptyResponseJson
