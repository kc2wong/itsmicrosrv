package com.exiasoft.itsstaticdata.service.impl.client

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.service.client.BaseClientServiceImpl
import com.exiasoft.itsstaticdata.CONTEXT_PATH_INTERNAL
import com.exiasoft.itsstaticdata.model.Exchange
import com.exiasoft.itsstaticdata.service.ExchangeService
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono

class ExchangeServiceImpl(appName: String, loadBalancingClient: LoadBalancerClient, webClientBuilder: WebClient.Builder) :
        BaseClientServiceImpl<String, Exchange>(appName, "$CONTEXT_PATH_INTERNAL/sapi/exchanges", loadBalancingClient, webClientBuilder), ExchangeService {

    override fun addQueryParameterForFindByIdentifier(uriBuilder: UriBuilder, id: String) {
        uriBuilder.queryParam("exchangeCode", id)
    }

    override fun find(authenToken: AuthenticationToken, nameDefLang: String?, pageable: Pageable): Mono<Page<Exchange>> {
        return super.find(authenToken, pageable) {builder ->
            nameDefLang?.let { builder.queryParam("nameDefLang", it) }
        }
    }

}