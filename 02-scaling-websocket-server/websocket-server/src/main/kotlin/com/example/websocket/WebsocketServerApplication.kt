package com.example.websocket

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class WebsocketServerApplication

fun main(args: Array<String>) {
	runApplication<WebsocketServerApplication>(*args)
}
