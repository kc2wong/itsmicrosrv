package com.exiasoft.itsconfig

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
class ItsconfigApplication

fun main(args: Array<String>) {
    runApplication<ItsconfigApplication>(*args)
}
