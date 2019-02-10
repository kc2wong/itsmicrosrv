package com.exiasoft.itsstaticdata.service.impl.client

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.service.client.BaseSimpleObjectClientServiceImpl
import com.exiasoft.itsstaticdata.CONTEXT_PATH_INTERNAL
import com.exiasoft.itsstaticdata.model.SimpleExchange
import com.exiasoft.itsstaticdata.model.SimpleExchangeData
import com.exiasoft.itsstaticdata.service.SimpleExchangeService
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono

class SimpleExchangeServiceImpl(appName: String, loadBalancingClient: LoadBalancerClient, webClientBuilder: WebClient.Builder) :
        BaseSimpleObjectClientServiceImpl<String, SimpleExchange, SimpleExchangeData>(appName, "$CONTEXT_PATH_INTERNAL/sapi/simple-exchanges", loadBalancingClient, webClientBuilder), SimpleExchangeService {

    override fun addQueryParameterForFindByIdentifier(uriBuilder: UriBuilder, id: String) {
        uriBuilder.queryParam("exchangeCode", id)
    }

    override fun find(authenToken: AuthenticationToken, nameDefLang: String?, pageable: Pageable): Mono<Page<SimpleExchange>> {
        return super.find(authenToken, pageable) { builder ->
            nameDefLang?.let { builder.queryParam("nameDefLang", it) }
        } .map { PageImpl(it.content.map { v -> v as SimpleExchange }.toList(), it.pageable, it.totalElements) }
    }

}