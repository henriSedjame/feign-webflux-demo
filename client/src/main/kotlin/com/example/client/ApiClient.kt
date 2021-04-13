package com.example.client

import feign.Headers
import feign.RequestLine
import reactor.core.publisher.Flux

@Headers("Accept: */*")
interface ApiClient {

    @RequestLine("GET /tasks")
    @Headers("Content-Type: text/event-stream")
    fun tasks(): Flux<String>

}
