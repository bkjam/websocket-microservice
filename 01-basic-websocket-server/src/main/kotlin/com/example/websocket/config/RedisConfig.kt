package com.example.websocket.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveKeyCommands
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.ReactiveStringCommands
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {
    @Bean
    fun reactiveRedisTemplate(factory: LettuceConnectionFactory): ReactiveRedisTemplate<String, String> {
        val serializer = Jackson2JsonRedisSerializer(String::class.java)
        val builder = RedisSerializationContext.newSerializationContext<String, String>(StringRedisSerializer())
        val context = builder.value(serializer).build()
        return ReactiveRedisTemplate(factory, context)
    }

    @Bean
    fun keyCommands(reactiveRedisConnectionFactory: ReactiveRedisConnectionFactory): ReactiveKeyCommands {
        return reactiveRedisConnectionFactory.reactiveConnection.keyCommands()
    }

    @Bean
    fun stringCommands(reactiveRedisConnectionFactory: ReactiveRedisConnectionFactory): ReactiveStringCommands {
        return reactiveRedisConnectionFactory.reactiveConnection.stringCommands()
    }
}
