package com.exiasoft.itsauthen.config

import com.exiasoft.itsauthen.service.impl.JwtTokenProvider
import com.exiasoft.itsauthen.service.impl.client.DataEntitlementServiceImpl
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.TokenProvider
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.web.reactive.BindingContext
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Configuration
@ConditionalOnClass(WebFluxConfigurer::class, LoadBalancerClient::class)
open class ItsAuthenServiceClientAutoConfig(
        @Value("\${jwt.secret}") val secretKey: String,
        val functionListConfig: FunctionListConfig,
        @Value("\${itsauthen.application.name:itsauthen}") val appName: String,
        val loadBalancingClient: LoadBalancerClient,
        val webClientBuilder: WebClient.Builder
) : WebFluxConfigurer {

    private val logger = KotlinLogging.logger {}

    @Bean
    fun tokenProvider(): TokenProvider {
        return JwtTokenProvider(secretKey, functionListConfig)
    }

    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        logger.info("configureArgumentResolvers() - start")
        val jwtArgumentResolver = object : HandlerMethodArgumentResolver {
            override fun supportsParameter(parameter: MethodParameter): Boolean {
                return AuthenticationToken::class.java == parameter.parameterType
            }

            override fun resolveArgument(parameter: MethodParameter, bindingContext: BindingContext, exchange: ServerWebExchange): Mono<Any> {
                logger.info("resolveArgument - keys : " + exchange.request.headers.keys)
                val authorization = exchange.request.headers["Authorization"] ?.let {
                    if (it.isEmpty()) "" else it.first()
                } ?: ""
                val token = tokenProvider().createToken(authorization)
                return Mono.just(tokenProvider().createToken(authorization))
            }
        }
        configurer.addCustomResolver(jwtArgumentResolver)
        super.configureArgumentResolvers(configurer)
    }

    @Bean
    @ConditionalOnMissingBean(name = ["DataEntitlementServiceImpl"])
    fun dataEntitlementServiceClient() : DataEntitlementServiceImpl {
        return DataEntitlementServiceImpl(appName, loadBalancingClient, webClientBuilder)
    }

}
