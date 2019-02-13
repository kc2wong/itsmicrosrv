package com.exiasoft.itscommon.resource

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.HEADER_AUTHORIZATION
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.core.env.Environment
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.Inet4Address


abstract class BaseExperienceApiResource(val loadBalancingClient: LoadBalancerClient,
                                         val webClientBuilder: WebClient.Builder
) : BaseResource() {

    @Autowired
    private lateinit var environment: Environment

    fun consumeResource(authenToken: AuthenticationToken, appName: String, uri: String, requestParams: List<Map<String, String>>): Flux<Any> {
        val client = loadBalancingClient.choose(appName)
        val (scheme, host, port) = client?.let {
            Triple(it.uri.scheme, it.uri.host, it.uri.port)
        } ?: Triple("http", Inet4Address.getLocalHost().hostAddress, environment.getProperty("server.port", Int::class.java, 8080))
        return Flux.merge(requestParams.map { rp ->
            val mvm = LinkedMultiValueMap<String, String>()
            rp.forEach { t, u -> mvm.add(t, u) }
            webClientBuilder.build().get().uri {
                it.scheme(scheme).host(host).port(port).path(uri)
                it.queryParams(mvm)
                it.build()
            }
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    // Quick solution is to set token to header, It is better to add webfilter to store all headers and pass to downstream
                    .header(HEADER_AUTHORIZATION, authenToken.toString())
                    .retrieve()
                    .bodyToFlux(Any::class.java)
        })
    }

    fun consumeResourceAndCollect(authenToken: AuthenticationToken, appName: String, uri: String, requestParams: List<Map<String, String>>): Mono<List<Any>> {
        return consumeResource(authenToken, appName, uri, requestParams).collectList()
    }
}