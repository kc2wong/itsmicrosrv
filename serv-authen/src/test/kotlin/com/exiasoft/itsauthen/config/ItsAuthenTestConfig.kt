package com.exiasoft.itsauthen.config

import com.exiasoft.itsauthen.service.impl.JwtTokenProvider
import com.exiasoft.itscommon.config.ArgumentResolverConfiguration
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(value = [ArgumentResolverConfiguration::class, FunctionListConfig::class, JwtTokenProvider::class])
class ItsAuthenTestConfig {
    @Bean
    fun objectMapper(): ObjectMapper {
        val mapper = ObjectMapper()
        mapper.setVisibility(mapper.serializationConfig.defaultVisibilityChecker
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY))
        return mapper
    }
}