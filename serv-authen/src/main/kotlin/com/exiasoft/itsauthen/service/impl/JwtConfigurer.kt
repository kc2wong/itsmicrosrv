package com.exiasoft.itsauthen.service.impl

import com.exiasoft.itscommon.authen.TokenProvider
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JwtConfigurer(private val tokenProvider: TokenProvider) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(http: HttpSecurity?) {
        val customFilter = JwtFilter(tokenProvider)
        http?.let {
            it.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter::class.java)
        }
    }

}