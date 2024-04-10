package com.nokhyun.samplestructure.pattern.proxy

import java.util.Base64.Decoder
import java.util.Base64.Encoder

class ChatServiceSecureProxy(
    private val encoder: Encoder,
    private val decoder: Decoder
): ChatService {

    private val defaultChatService = DefaultChatService()

    override fun sendMessage(msg: String) {
        defaultChatService.sendMessage(encoder.encodeToString(msg.toByteArray()))
    }

    override fun getMessage(): String {
        val msg = defaultChatService.getMessage()
        return decoder.decode(msg).decodeToString()
    }
}