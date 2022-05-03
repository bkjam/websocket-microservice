package com.example.websocket.controller

import com.example.websocket.model.NewMessageRequest
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/notification")
class NotificationController(private val template: SimpMessagingTemplate) {
    @PostMapping
    fun newMessage(@RequestBody request: NewMessageRequest) {
        template.convertAndSend(request.topic, request.message)
    }
}
