package com.exiasoft.itsstaticdata

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class ItsstaticdataApplication

fun main(args: Array<String>) {
    runApplication<ItsstaticdataApplication>(*args)
}
