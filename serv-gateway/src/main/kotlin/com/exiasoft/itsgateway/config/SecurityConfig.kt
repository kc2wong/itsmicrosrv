package com.exiasoft.itsgateway.config

import com.exiasoft.itsauthen.service.impl.JwtAuthenticationConverter
import com.exiasoft.itscommon.authen.TokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig(val tokenProvider: TokenProvider) {

    @Bean
    fun securitygWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        val authFilter = AuthenticationWebFilter(ReactiveAuthenticationManager {
            if (it != null) Mono.just(it) else Mono.empty()
        })
        authFilter.setServerAuthenticationConverter(JwtAuthenticationConverter(tokenProvider))

        return http.authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .pathMatchers("/actuator/**").permitAll()
                .pathMatchers("/authentication/**").permitAll()
                .pathMatchers("/**").authenticated().and().httpBasic()
                .and()
                .addFilterAt(authFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf()
                .disable()
                .build()
    }
}
