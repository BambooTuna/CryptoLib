package com.github.BambooTuna.CryptoLib.restAPI.client.discord.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.APIInterface.APIList._
import com.github.BambooTuna.CryptoLib.restAPI.client.discord.DiscordRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol.HttpRequestElement

case class WebhookImpl(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends DiscordRestAPI[WebhookBodyImpl, WebhookQueryParametersImpl, WebhookResponseImpl]
case class WebhookBodyImpl(
                            username: String,
                            content: String
                          ) extends WebhookBody
case class WebhookQueryParametersImpl() extends WebhookQueryParameters
case class WebhookResponseImpl() extends WebhookResponse
