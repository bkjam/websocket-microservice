package com.example.websocket.controller

import com.example.common.model.StreamDataEvent
import com.example.websocket.service.RedisStreamProducer
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller

@Controller
class WebsocketController(private val redisStreamProducer: RedisStreamProducer) {
    @MessageMapping("/test")
    fun greetMessage(@Payload message: String) {
        val event = StreamDataEvent(message)
        redisStreamProducer.publishEvent("TEST_EVENT_TO_BACKEND", event)
    }
}
