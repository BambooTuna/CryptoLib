package com.github.BambooTuna.CryptoLib.restAPI.client.wordpress

import akka.http.scaladsl.model.HttpMethods
import com.github.BambooTuna.CryptoLib.restAPI.client.wordpress.APIList._
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.HttpRequestElement
import com.github.BambooTuna.CryptoLib.restAPI.model.{ApiKey, Endpoint, Path}

class WordPressRestAPIs(val apiKey: ApiKey)(val host: String) {

  val endpoint = Endpoint(
    scheme = "https",
    host = host
  )

  val articlePosting = ArticlePosting(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.POST,
      path = Path("/wp-json/wp/v2/posts")
    )
  )

  val mediaPosting = MediaPosting(
    apiKey = apiKey,
    httpRequestElement = HttpRequestElement(
      endpoint = endpoint,
      method = HttpMethods.POST,
      path = Path("/wp-json/wp/v2/media")
    )
  )

}