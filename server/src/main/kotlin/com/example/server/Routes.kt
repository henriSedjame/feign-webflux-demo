package com.example.server

import kotlinx.coroutines.reactive.asFlow
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.coRouter
import reactor.core.publisher.Sinks
import java.util.*

@Configuration
class Routes(val sink: Sinks.Many<String>, val task: TaskTimed) {

    val timer = Timer()

    @Bean
    fun taskRoutes() = coRouter {
        GET("/tasks"){
            timer.scheduleAtFixedRate(task, 1000, 1000)
            ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .bodyAndAwait(sink.asFlux().asFlow())
        }
    }

}
