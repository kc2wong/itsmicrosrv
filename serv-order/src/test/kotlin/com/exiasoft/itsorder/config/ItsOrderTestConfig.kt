package com.exiasoft.itsorder.config

import com.exiasoft.itsauthen.config.FunctionListConfig
import com.exiasoft.itsauthen.config.ItsAuthenServiceClientAutoConfig
import com.exiasoft.itsauthen.service.impl.JwtTokenProvider
import com.exiasoft.itscommon.config.ArgumentResolverConfiguration
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.http.HttpHeaders
import org.springframework.http.client.reactive.ClientHttpConnector
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.ExchangeFunction
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilderFactory
import java.net.URI
import java.util.function.Consumer

@Configuration
@ComponentScan("com.exiasoft.itsorder")
@Import(value = [ArgumentResolverConfiguration::class, ItsAuthenServiceClientAutoConfig::class, FunctionListConfig::class, JwtTokenProvider::class])
class ItsOrderTestConfig