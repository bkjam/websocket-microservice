package com.example.websocket.config

import com.example.common.model.StreamDataEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.connection.stream.StreamOffset
import org.springframework.data.redis.stream.StreamListener
import org.springframework.data.redis.stream.StreamMessageListenerContainer
import org.springframework.data.redis.stream.Subscription
import java.time.Duration

@Configuration
class RedisStreamConfig(private val streamListener: StreamListener<String, ObjectRecord<String, StreamDataEvent>>) {

    private fun initListenerContainer(redisConnectionFactory: RedisConnectionFactory): StreamMessageListenerContainer<String, ObjectRecord<String, StreamDataEvent>> {
        val options = StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .targetType(StreamDataEvent::class.java)
                .build()
        return StreamMessageListenerContainer.create(redisConnectionFactory, options)
    }

    @Bean("TestEventSubscription")
    fun subscription(redisConnectionFactory: RedisConnectionFactory): Subscription {
        val listenerContainer = initListenerContainer(redisConnectionFactory)
        val subscription = listenerContainer.receive(StreamOffset.latest("TEST_EVENT_TO_WEBSOCKET_SERVER"), streamListener)
        listenerContainer.start()
        return subscription
    }
}
