package com.exiasoft.itscommon.config

import com.exiasoft.itscommon.annotation.PageableDefault
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.web.reactive.BindingContext
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Configuration
@ConditionalOnClass(WebFluxConfigurer::class)
open class ArgumentResolverConfiguration(
        @Value("\${its.page.size:50}") val pageSize: Int,
        @Value("\${its.page.max.size:32767}") val pageMaxSize: Int
) : WebFluxConfigurer {

    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        val pagableArgumentResolver = object : HandlerMethodArgumentResolver {
            override fun supportsParameter(parameter: MethodParameter): Boolean {
                return Pageable::class.java == parameter.parameterType
            }

            override fun resolveArgument(parameter: MethodParameter, bindingContext: BindingContext, exchange: ServerWebExchange): Mono<Any> {
                val default = parameter.getParameterAnnotation(PageableDefault::class.java)
                val queryParams = exchange.request.queryParams
                val page = queryParams.getFirst("page")?.let { Integer.parseInt(it) } ?: 0
                val size = queryParams.getFirst("size")?.let { Integer.parseInt(it) } ?: default ?.let { it.size } ?: pageSize
                val sort = queryParams["sort"] ?: default ?.let { it.sort.toList() } ?: emptyList<String>()

                val orders = sort.mapNotNull {
                    val propAndOrder = it.split(",")
                    when (propAndOrder.size) {
                        0 -> null
                        1 -> Sort.Order(Sort.DEFAULT_DIRECTION, propAndOrder[0])
                        else -> Sort.Order(if (Sort.Direction.ASC.toString().equals(propAndOrder[1].trim(), true)) Sort.Direction.ASC else Sort.Direction.DESC, propAndOrder[0])
                    }
                }
                val pageable = PageRequest.of(page,
                        if (size > 0) Math.min(size, pageMaxSize) else pageSize,
                        if (sort.isEmpty()) Sort.unsorted() else Sort.by(orders)
                )
                return Mono.just(pageable)
            }
        }
        configurer.addCustomResolver(pagableArgumentResolver)
        super.configureArgumentResolvers(configurer)
    }

}