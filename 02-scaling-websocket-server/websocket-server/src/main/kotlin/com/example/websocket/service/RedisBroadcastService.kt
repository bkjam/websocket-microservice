package com.example.websocket.service

import com.example.websocket.model.BroadcastEvent
import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.ReactiveSubscription
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class RedisBroadcastService(
    private val reactiveRedisTemplate: ReactiveRedisTemplate<String, BroadcastEvent>,
    private val websocketTemplate: SimpMessagingTemplate
) {
    companion object {
        private const val broadcastChannel = "BROADCAST-CHANNEL"
        private val logger = LoggerFactory.getLogger(RedisBroadcastService::class.java)
    }

    fun publish(event: BroadcastEvent) {
        logger.info("Broadcasting event... $event")
        reactiveRedisTemplate.convertAndSend(broadcastChannel, event).subscribe()
    }

    @PostConstruct
    fun subscribe() {
        reactiveRedisTemplate.listenTo(ChannelTopic.of(broadcastChannel))
            .map(ReactiveSubscription.Message<String, BroadcastEvent>::getMessage)
            .subscribe { message ->
                websocketTemplate.convertAndSend(message.topic, message.message)
            }
    }
}
