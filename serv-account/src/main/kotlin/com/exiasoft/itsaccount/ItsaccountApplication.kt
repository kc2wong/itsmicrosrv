package com.exiasoft.itsaccount

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class ItsaccountApplication

const val CONTEXT_PATH = "/account/v1"
const val CONTEXT_PATH_INTERNAL = "/account/int/v1"

fun main(args: Array<String>) {
	runApplication<ItsaccountApplication>(*args)
}

