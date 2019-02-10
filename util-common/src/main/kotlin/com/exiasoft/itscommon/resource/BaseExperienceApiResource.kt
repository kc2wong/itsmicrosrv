package com.exiasoft.itscommon.resource

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.HEADER_AUTHORIZATION
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


abstract class BaseExperienceApiResource(val loadBalancingClient: LoadBalancerClient,
                                         val webClientBuilder: WebClient.Builder
) : BaseResource() {

    fun consumeResource(authenToken: AuthenticationToken, appName: String, uri: String, requestParams: List<Map<String, String>>): Flux<Any> {
        val client = loadBalancingClient.choose(appName)
        return Flux.merge(requestParams.map { rp ->
            val mvm = LinkedMultiValueMap<String, String>()
            rp.forEach { t, u -> mvm.add(t, u) }
            webClientBuilder.build().get().uri {
                it.scheme(client.uri.scheme).host(client.uri.host).port(client.uri.port).path(uri)
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