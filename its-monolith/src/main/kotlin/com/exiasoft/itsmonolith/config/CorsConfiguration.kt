package com.exiasoft.itsmonolith.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "cors-configurations")
class CorsConfiguration() {
    lateinit var allowedOrigins: List<String>
    lateinit var allowedMethods: List<String>
    lateinit var allowedHeaders: List<String>
    lateinit var exposedHeaders: List<String>
    var maxAge: Long = 0
}