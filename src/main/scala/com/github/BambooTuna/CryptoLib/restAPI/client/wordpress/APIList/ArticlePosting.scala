package com.github.BambooTuna.CryptoLib.restAPI.client.wordpress.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.wordpress.APIList.WordPressEnumDefinition.{BoolStatus, PostingStatus}
import com.github.BambooTuna.CryptoLib.restAPI.client.wordpress.WordPressRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

case class ArticlePosting(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends WordPressRestAPI[ArticlePostingBody, ArticlePostingQueryParameters, ArticlePostingResponse]

case class ArticlePostingBody(
                               title: String,
                               content: String, // 本文
                               excerpt: String = "", // 抜粋
                               featured_media: Long, // アイキャッチ画像ID
                               categories: Long = 1L, // 1L = 未分類
                               tags: String = "", // 複数ある時は "1,12,33"
                               comment_status: BoolStatus = BoolStatus.Open,
                               ping_status: BoolStatus = BoolStatus.Open,
                               status: PostingStatus = PostingStatus.Draft
                      ) extends EmptyEntityRequestJson

case class ArticlePostingQueryParameters() extends EmptyQueryParametersJson

case class ArticlePostingResponse(
                                 id: Long,
                                 date: String,
                                 date_gmt: String,
                                 status: PostingStatus
                                 ) extends EmptyResponseJson
