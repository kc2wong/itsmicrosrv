package com.exiasoft.itsorder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class ItsOrderApplication

fun main(args: Array<String>) {
	runApplication<ItsOrderApplication>(*args)
}

