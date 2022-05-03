package com.example.websocket.controller

import com.example.websocket.service.RedisService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller

@Controller
class WebsocketController(private val redisService: RedisService) {
    @MessageMapping("/greet")
    fun greetMessage(@Payload message: String) {
        redisService.publish("GREETING_CHANNEL_OUTBOUND", message)
    }
}
