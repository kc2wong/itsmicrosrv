package com.exiasoft.itsmarketdata

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class ItsmarketdataApplication

const val CONTEXT_PATH = "/marketdata/v1"
const val CONTEXT_PATH_INTERNAL = "/marketdata/int/v1"

fun main(args: Array<String>) {
    runApplication<ItsmarketdataApplication>(*args)
}
