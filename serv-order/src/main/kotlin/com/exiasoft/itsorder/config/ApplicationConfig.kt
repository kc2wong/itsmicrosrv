package com.exiasoft.itsorder.config

import com.exiasoft.itsauthen.access.FunctionCodePermissionEvaluator
import com.exiasoft.itscommon.bean.XStreamProvider
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfig {

    private val logger = KotlinLogging.logger {}

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    @Value("\${application.function.code:}#{T(java.util.Collections).emptyList()}")
    private lateinit var functionCode: List<String>

    @Bean
    @ConditionalOnMissingBean
    fun xStreamProvider() : XStreamProvider {
        return XStreamProvider()
    }

    @Bean
    fun permissionEvaluator(): FunctionCodePermissionEvaluator {
        return FunctionCodePermissionEvaluator()
    }

}