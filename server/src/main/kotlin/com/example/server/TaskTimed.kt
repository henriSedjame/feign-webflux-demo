package com.example.server

import org.springframework.stereotype.Component
import reactor.core.publisher.Sinks
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Component
data class TaskTimed(val sink: Sinks.Many<String>): TimerTask() {
    override fun run() {
        sink.tryEmitNext(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
    }
}
