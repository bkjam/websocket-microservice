package com.example.websocket.model

data class NewMessageRequest(
    val topic: String,
    val message: String
)
