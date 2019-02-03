package com.exiasoft.itsauthen.config

import com.exiasoft.itscommon.bean.XStreamProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@RefreshScope
@Component
class ApplicationConfig {

    @Value("\${companyCode}")
    lateinit var companyCode: String

    @Value("\${jwt.secret}")
    lateinit var jwtSecret: String

    @Value("\${jwt.token-validity-in-seconds}")
    var jwtTokenValidityInSeconds: Long = 0

    @Value("\${jwt.token-validity-in-seconds-for-remember-me}")
    var jwtTokenValidityInSecondsForRememberMe: Long = 0

    @Bean
    @ConditionalOnMissingBean
    fun xStreamProvider() : XStreamProvider {
        return XStreamProvider()
    }

}