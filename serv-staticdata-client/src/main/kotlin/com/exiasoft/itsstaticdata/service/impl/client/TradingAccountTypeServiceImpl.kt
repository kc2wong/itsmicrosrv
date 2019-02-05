package com.exiasoft.itsstaticdata.service.impl.client

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.service.client.BaseClientServiceImpl
import com.exiasoft.itsstaticdata.CONTEXT_PATH_INTERNAL
import com.exiasoft.itsstaticdata.model.TradingAccountType
import com.exiasoft.itsstaticdata.service.TradingAccountTypeService
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono

class TradingAccountTypeServiceImpl(appName: String, loadBalancingClient: LoadBalancerClient, webClientBuilder: WebClient.Builder) :
        BaseClientServiceImpl<String, TradingAccountType>(appName,"$CONTEXT_PATH_INTERNAL/sapi/trading-account-types", loadBalancingClient, webClientBuilder), TradingAccountTypeService {

    override fun addQueryParameterForFindByIdentifier(uriBuilder: UriBuilder, id: String) {
        uriBuilder.queryParam("tradingAccountTypeCode", id)
    }

    override fun find(authenToken: AuthenticationToken, description: String?, pageable: Pageable): Mono<Page<TradingAccountType>> {
        return super.find(authenToken, pageable) { builder ->
            description?.let { builder.queryParam("description", it) }
        }
    }
}
