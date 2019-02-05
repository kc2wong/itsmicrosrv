package com.exiasoft.itsmarketdata

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class ItsmarketdataApplication

fun main(args: Array<String>) {
    runApplication<ItsmarketdataApplication>(*args)
}
