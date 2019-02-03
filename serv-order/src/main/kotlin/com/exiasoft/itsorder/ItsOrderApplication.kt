package com.exiasoft.itsorder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class ItsOrderApplication

const val CONTEXT_PATH = "/order/v1"
const val CONTEXT_PATH_INTERNAL = "/order/int/v1"

fun main(args: Array<String>) {
	runApplication<ItsOrderApplication>(*args)
}

