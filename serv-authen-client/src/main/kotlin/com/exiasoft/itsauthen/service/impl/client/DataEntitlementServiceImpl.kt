package com.exiasoft.itsauthen.service.impl.client

import com.exiasoft.itsauthen.CONTEXT_PATH_INTERNAL
import com.exiasoft.itsauthen.model.security.DataEntitlement
import com.exiasoft.itsauthen.service.DataEntitlementService
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.HEADER_AUTHORIZATION
import com.exiasoft.itscommon.service.client.BaseClientServiceImpl
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono

class DataEntitlementServiceImpl(appName: String, loadBalancingClient: LoadBalancerClient, webClientBuilder: WebClient.Builder) :
        BaseClientServiceImpl<String, DataEntitlement>(appName,"$CONTEXT_PATH_INTERNAL/sapi/data-entitlement", loadBalancingClient, webClientBuilder), DataEntitlementService {

    override fun addQueryParameterForFindByIdentifier(uriBuilder: UriBuilder, id: String) {
        uriBuilder.queryParam("userOid", id)
    }

    override fun getDataEntitlement(authenToken: AuthenticationToken): Mono<DataEntitlement> {
        val client = loadBalancingClient.choose(appName)
        return webClientBuilder.build().get().uri {
            it.scheme(client.uri.scheme).host(client.uri.host).port(client.uri.port).path(baseUri)
            it.build()
        }.accept(MediaType.APPLICATION_JSON_UTF8)
                .header(HEADER_AUTHORIZATION, authenToken.toString())
                .retrieve()
                .bodyToMono(DataEntitlement::class.java)
                .switchIfEmpty(Mono.empty())
                .flatMap { Mono.just(it) }
    }
}
