package com.exiasoft.itsauthen

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ItsauthenApplication

const val CONTEXT_PATH = "/authentication/v1"
const val CONTEXT_PATH_INTERNAL = "/authentication/int/v1"

fun main(args: Array<String>) {
    runApplication<ItsauthenApplication>(*args)
}
