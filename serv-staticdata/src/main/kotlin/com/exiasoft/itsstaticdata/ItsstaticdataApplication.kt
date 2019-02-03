package com.exiasoft.itsstaticdata

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class ItsstaticdataApplication

const val CONTEXT_PATH = "/staticdata/v1"
const val CONTEXT_PATH_INTERNAL = "/staticdata/int/v1"

fun main(args: Array<String>) {
    runApplication<ItsstaticdataApplication>(*args)
}
