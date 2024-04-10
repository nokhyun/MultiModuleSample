package com.nokhyun.samplestructure.pattern.proxy

class DefaultChatService: ChatService {

    private var message: String = ""

    override fun sendMessage(msg: String) {
        println("sendMessage: $msg")
        message = msg
    }

    override fun getMessage(): String {
        return message
    }
}