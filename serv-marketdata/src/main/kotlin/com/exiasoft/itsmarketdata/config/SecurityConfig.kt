package com.exiasoft.itsmarketdata.config

import com.exiasoft.itscommon.authen.TokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig(tokenProvider: TokenProvider) {

    @Bean
    fun securitygWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http.authorizeExchange()
//                .pathMatchers("/**").authenticated()
                .pathMatchers("/**").permitAll()
                .and()
//                .formLogin()
//                .and()
                .csrf()
                .disable()
                .build()
    }
}