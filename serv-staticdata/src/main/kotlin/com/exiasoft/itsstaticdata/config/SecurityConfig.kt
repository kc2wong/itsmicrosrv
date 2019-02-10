package com.exiasoft.itsstaticdata.config

import com.exiasoft.itsauthen.service.impl.JwtAuthenticationConverter
import com.exiasoft.itscommon.authen.TokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import reactor.core.publisher.Mono

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig(val tokenProvider: TokenProvider) {

    @Bean
    fun securitygWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        val authFilter = AuthenticationWebFilter(ReactiveAuthenticationManager {
            authentication: Authentication -> Mono.just(authentication)
        })
        authFilter.setServerAuthenticationConverter(JwtAuthenticationConverter(tokenProvider))

        return http.authorizeExchange()
                .pathMatchers("/actuator/**").permitAll()
                .pathMatchers("/**").authenticated()
                .and()
                .addFilterAt(authFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf()
                .disable()
                .build()
    }

}