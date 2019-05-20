package com.github.BambooTuna.CryptoLib.restAPI.client.discord.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.discord.DiscordRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

case class WebhookImpl(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends DiscordRestAPI[WebhookBodyImpl, WebhookQueryParametersImpl, WebhookResponseImpl]
case class WebhookBodyImpl(
                            username: String,
                            content: String
                          ) extends EmptyEntityRequestJson
case class WebhookQueryParametersImpl() extends EmptyQueryParametersJson
case class WebhookResponseImpl() extends EmptyResponseJson
