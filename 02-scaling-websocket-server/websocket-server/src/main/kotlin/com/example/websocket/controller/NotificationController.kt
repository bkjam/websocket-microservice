package com.example.websocket.controller

import com.example.websocket.model.BroadcastEvent
import com.example.websocket.model.NewMessageRequest
import com.example.websocket.service.RedisBroadcastService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/notification")
class NotificationController(private val redisBroadcastService: RedisBroadcastService) {
    @PostMapping
    fun newMessage(@RequestBody request: NewMessageRequest) {
        val event = BroadcastEvent(request.topic, request.message)
        redisBroadcastService.publish(event)
    }
}
