package com.example.websocket.service

import com.example.common.model.StreamDataEvent
import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.stream.StreamListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class RedisStreamConsumer(
    private val websocketTemplate: SimpMessagingTemplate
): StreamListener<String, ObjectRecord<String, StreamDataEvent>> {
    companion object {
        private val logger = LoggerFactory.getLogger(RedisStreamConsumer::class.java)
    }

    override fun onMessage(record: ObjectRecord<String, StreamDataEvent>) {
        logger.info("[NEW] --> received message: ${record.value} from stream: ${record.stream}")
        record.value.topic?.let { destination ->
            websocketTemplate.convertAndSend(destination, record.value.message)
        }
    }
}
