package com.exiasoft.itsauthen.service.impl

import com.exiasoft.itscommon.authen.HEADER_AUTHORIZATION
import com.exiasoft.itscommon.authen.TokenProvider
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.util.StringUtils
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class JwtAuthenticationConverter(val tokenProvider: TokenProvider) : ServerAuthenticationConverter {

    override fun convert(exchange: ServerWebExchange?): Mono<Authentication> {
        val rtn = exchange ?.let { exch ->
            resolveToken(exch.request) ?.let { jwt ->
                val token = tokenProvider.createToken(jwt)
                if (token.validate()) token.getAuthentication() else null
            }
        }
        return rtn ?.let { Mono.just(it) } ?: Mono.empty()
    }

    private fun resolveToken(request: ServerHttpRequest): String? {
        val bearerToken = request.headers.getFirst(HEADER_AUTHORIZATION) ?: ""
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7, bearerToken.length)
        } else ""
    }
}