package com.exiasoft.itszipkin

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import zipkin.server.EnableZipkinServer

@SpringBootApplication
@EnableEurekaClient
@EnableZipkinServer
class ItszipkinApplication

fun main(args: Array<String>) {
	SpringApplication.run(ItszipkinApplication::class.java, *args)
}

