package com.exiasoft.itsmonolith.config

import com.exiasoft.itsauthen.access.FunctionCodePermissionEvaluator
import com.exiasoft.itscommon.bean.XStreamProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScans
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Configuration
@ComponentScans(value = [
    ComponentScan(basePackages = ["com.exiasoft.itsauthen.mapper", "com.exiasoft.itsauthen.service", "com.exiasoft.itsauthen.resource"]),
    ComponentScan(basePackages = ["com.exiasoft.itsstaticdata.mapper", "com.exiasoft.itsstaticdata.service", "com.exiasoft.itsstaticdata.resource"]),
    ComponentScan(basePackages = ["com.exiasoft.itsaccount.mapper", "com.exiasoft.itsaccount.service", "com.exiasoft.itsaccount.resource"]),
    ComponentScan(basePackages = ["com.exiasoft.itsorder.mapper", "com.exiasoft.itsorder.service", "com.exiasoft.itsorder.resource"]),
    ComponentScan(basePackages = ["com.exiasoft.itsmarketdata.mapper", "com.exiasoft.itsmarketdata.service", "com.exiasoft.itsmarketdata.resource"])
])
class ApplicationConfig(val corsConfiguration : com.exiasoft.itsmonolith.config.CorsConfiguration) {

    @Bean
    fun corsWebFilter(): CorsWebFilter {
        val corsConfig = CorsConfiguration()
        corsConfig.allowedOrigins = corsConfiguration.allowedOrigins
        corsConfig.maxAge = corsConfiguration.maxAge
        corsConfig.allowedMethods = corsConfiguration.allowedMethods
        corsConfig.allowedHeaders = corsConfiguration.allowedHeaders
        corsConfig.exposedHeaders = corsConfiguration.exposedHeaders
        println(corsConfiguration.allowedHeaders)
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfig)

        return CorsWebFilter(source)
    }

    @Bean
    @ConditionalOnMissingBean
    fun xStreamProvider(): XStreamProvider {
        return XStreamProvider()
    }

    @Bean
    fun permissionEvaluator(): FunctionCodePermissionEvaluator {
        return FunctionCodePermissionEvaluator()
    }

}