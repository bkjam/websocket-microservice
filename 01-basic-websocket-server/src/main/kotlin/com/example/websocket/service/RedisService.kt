package com.example.websocket.service

import org.springframework.data.redis.connection.ReactiveSubscription
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class RedisService(
    private val reactiveRedisTemplate: ReactiveRedisTemplate<String, String>,
    private val websocketTemplate: SimpMessagingTemplate
) {
    fun publish(topic: String, message: String) {
        reactiveRedisTemplate.convertAndSend(topic, message).subscribe()
    }

    fun subscribe(channelTopic: String, destination: String) {
        reactiveRedisTemplate.listenTo(ChannelTopic.of(channelTopic))
            .map(ReactiveSubscription.Message<String, String>::getMessage)
            .subscribe { message ->
                websocketTemplate.convertAndSend(destination, message)
            }
    }

    @PostConstruct
    fun subscribe() {
        subscribe("GREETING_CHANNEL_INBOUND", "/topic/greetings")
    }
}
