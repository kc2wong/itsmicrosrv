package com.exiasoft.itsstaticdata.service.impl.client

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.service.client.BaseClientServiceImpl
import com.exiasoft.itsstaticdata.CONTEXT_PATH_INTERNAL
import com.exiasoft.itsstaticdata.model.ExchangeBoard
import com.exiasoft.itsstaticdata.service.ExchangeBoardService
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono

class ExchangeBoardServiceImpl(appName: String, loadBalancingClient: LoadBalancerClient, webClientBuilder: WebClient.Builder) :
        BaseClientServiceImpl<ExchangeBoard.Id, ExchangeBoard>(appName, "$CONTEXT_PATH_INTERNAL/sapi/exchange-boards", loadBalancingClient, webClientBuilder), ExchangeBoardService {

    override fun addQueryParameterForFindByIdentifier(uriBuilder: UriBuilder, id: ExchangeBoard.Id) {
        uriBuilder.queryParam("exchangeOid", id.exchangeOid)
                .queryParam("exchangeBoardCode", id.exchangeBoardCode)
    }

    override fun find(authenToken: AuthenticationToken, exchangeOid: String?, exchangeBoardCode: String?, nameDefLang: String?, pageable: Pageable): Mono<Page<ExchangeBoard>> {
        return super.find(authenToken, pageable) { builder ->
            exchangeOid?.let { builder.queryParam("exchangeOid", it) }
            exchangeBoardCode?.let { builder.queryParam("exchangeBoardCode", it) }
            nameDefLang?.let { builder.queryParam("nameDefLang", it) }
        }
    }

}