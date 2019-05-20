package com.github.BambooTuna.CryptoLib.restAPI.client.discord.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.discord.DiscordRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

case class Webhook(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends DiscordRestAPI[WebhookBody, WebhookQueryParameters, WebhookResponse]
case class WebhookBody(
                            username: String,
                            content: String
                          ) extends EmptyEntityRequestJson
case class WebhookQueryParameters() extends EmptyQueryParametersJson
case class WebhookResponse() extends EmptyResponseJson
