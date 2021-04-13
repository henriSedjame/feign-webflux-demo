package com.example.client

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import reactivefeign.webclient.WebReactiveFeign

@SpringBootApplication
class ClientApplication{

    @Bean
    fun client() = WebReactiveFeign
        .builder<ApiClient>()
        .target(ApiClient::class.java, "http://localhost:8081")
}

fun main(args: Array<String>) {
    val runApplication = runApplication<ClientApplication>(*args)

    val client = runApplication.getBean(ApiClient::class.java)

    client.tasks().subscribe {
        println(it)
    }
}
