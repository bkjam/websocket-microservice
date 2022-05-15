package com.example.backend.config

import com.example.common.model.StreamDataEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.stream.Consumer
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.connection.stream.ReadOffset
import org.springframework.data.redis.connection.stream.StreamOffset
import org.springframework.data.redis.stream.StreamListener
import org.springframework.data.redis.stream.StreamMessageListenerContainer
import org.springframework.data.redis.stream.Subscription
import java.time.Duration

@Configuration
class RedisStreamConfig(
        private val streamListener: StreamListener<String, ObjectRecord<String, StreamDataEvent>>,
        @Value("\${spring.application.name}") private val applicationName: String
) {
    @Bean
    fun subscription(redisConnectionFactory: RedisConnectionFactory): Subscription {
        val options = StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .targetType(StreamDataEvent::class.java)
                .build()
        val listenerContainer = StreamMessageListenerContainer.create(redisConnectionFactory, options)
        val subscription = listenerContainer.receiveAutoAck(
                Consumer.from("CONSUMER_GROUP", applicationName),
                StreamOffset.create("TEST_EVENT_TO_BACKEND", ReadOffset.lastConsumed()),
                streamListener
        )
        listenerContainer.start()
        return subscription
    }
}




















