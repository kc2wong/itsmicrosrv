package com.exiasoft.itsgateway.filter

import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class IpAddressWebFilter : WebFilter {

    private val logger = KotlinLogging.logger {}

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        if (logger.isDebugEnabled) {
            val ipAddress = exchange.request.remoteAddress?.let { it.address.hostAddress }
            logger.debug("Received request from = {}", ipAddress)
        }
        return exchange.request.remoteAddress?.let {remoteAddress ->
            val request = exchange.request.mutate()
                    .headers { it.add("X-Forwarded-For", remoteAddress.address.hostAddress) }
                    .build()
            chain.filter(exchange.mutate().request(request).build())
        } ?: chain.filter(exchange)
    }
}