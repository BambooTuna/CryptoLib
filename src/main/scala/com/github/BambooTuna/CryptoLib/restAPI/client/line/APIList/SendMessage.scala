package com.github.BambooTuna.CryptoLib.restAPI.client.line.APIList

import com.github.BambooTuna.CryptoLib.restAPI.client.line.APIList.LineEnumDefinition.MessageType
import com.github.BambooTuna.CryptoLib.restAPI.client.line.LineRestAPI
import com.github.BambooTuna.CryptoLib.restAPI.model.ApiKey
import com.github.BambooTuna.CryptoLib.restAPI.model.Protocol._

case class SendMessage(apiKey: ApiKey, httpRequestElement: HttpRequestElement) extends LineRestAPI[SendMessageBody, SendMessageQueryParameters, SendMessageResponse]

case class MessageData(
                      `type`: MessageType = MessageType.Text,
                      text: String
                      )
case class SendMessageBody(
                        to: String,
                        message: List[MessageData]
                      ) extends EmptyEntityRequestJson
case class SendMessageQueryParameters() extends EmptyQueryParametersJson
case class SendMessageResponse() extends EmptyResponseJson
