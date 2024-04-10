package com.nokhyun.samplestructure.pattern.proxy

interface ChatService {
    fun sendMessage(msg: String)
    fun getMessage(): String
}