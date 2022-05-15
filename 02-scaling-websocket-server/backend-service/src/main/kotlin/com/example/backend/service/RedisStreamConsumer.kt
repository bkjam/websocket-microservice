package com.example.backend.service

import com.example.common.model.StreamDataEvent
import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.stream.StreamListener
import org.springframework.stereotype.Service

@Service
class RedisStreamConsumer: StreamListener<String, ObjectRecord<String, StreamDataEvent>> {
    companion object {
        private val logger = LoggerFactory.getLogger(RedisStreamConsumer::class.java)
    }

    override fun onMessage(record: ObjectRecord<String, StreamDataEvent>?) {
        logger.info("[NEW] --> received message: ${record?.value} from stream: ${record?.stream}")
    }
}
