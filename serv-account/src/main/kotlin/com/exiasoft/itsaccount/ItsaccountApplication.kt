package com.exiasoft.itsaccount

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class ItsaccountApplication

fun main(args: Array<String>) {
	runApplication<ItsaccountApplication>(*args)
}

