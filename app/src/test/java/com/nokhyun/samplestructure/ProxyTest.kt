package com.nokhyun.samplestructure

import org.junit.Before
import org.junit.Test
import com.nokhyun.samplestructure.pattern.proxy.ChatService
import com.nokhyun.samplestructure.pattern.proxy.ChatServiceSecureProxy
import com.nokhyun.samplestructure.pattern.proxy.DefaultChatService
import java.util.Base64

/**
 * Proxy DesignPattern
 * @see ChatService
 * @see ChatServiceSecureProxy
 * @see DefaultChatService
 * 내부에서 사용되는 오브젝트에 대한 래퍼.
 * 추가적인 로직구성을 할 수 있다.
 * 추가적인 접근 제어가 필요할 때 사용.
 * reference: https://www.droidcon.com/2024/04/02/kotlin-design-patterns-proxy-explained/
 * */
class ProxyTest {

    private lateinit var chatServiceSecureProxy: ChatService

    @Before
    fun init() {
        chatServiceSecureProxy = ChatServiceSecureProxy(Base64.getEncoder(), Base64.getDecoder())
    }

    @Test
    fun `프록시_패턴_실행`() {
        chatServiceSecureProxy.sendMessage("안녕하세요")
        println("getMessage: ${chatServiceSecureProxy.getMessage()}")
    }
}