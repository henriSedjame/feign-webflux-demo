package com.example.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Sinks

@SpringBootApplication
@RestController
class ServerApplication {
    @Bean
    fun sink() = Sinks.many().multicast().directAllOrNothing<String>()
}

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}

