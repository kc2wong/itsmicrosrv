package com.exiasoft.itsauthen.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "function")
class FunctionListConfig {
    lateinit var list: List<String>
}