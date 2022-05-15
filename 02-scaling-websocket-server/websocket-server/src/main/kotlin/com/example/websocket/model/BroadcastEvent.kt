package com.example.websocket.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class BroadcastEvent(
        @JsonProperty("topic") val topic: String,
        @JsonProperty("message") val message: String
): Serializable
