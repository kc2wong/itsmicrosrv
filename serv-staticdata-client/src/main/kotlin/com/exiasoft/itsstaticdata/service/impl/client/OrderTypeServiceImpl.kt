package com.exiasoft.itsstaticdata.service.impl.client

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.service.client.BaseClientServiceImpl
import com.exiasoft.itsstaticdata.CONTEXT_PATH_INTERNAL
import com.exiasoft.itsstaticdata.model.OrderType
import com.exiasoft.itsstaticdata.service.OrderTypeService
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono

class OrderTypeServiceImpl(appName: String, loadBalancingClient: LoadBalancerClient, webClientBuilder: WebClient.Builder) :
        BaseClientServiceImpl<String, OrderType>(appName,"$CONTEXT_PATH_INTERNAL/sapi/order-types", loadBalancingClient, webClientBuilder), OrderTypeService {

    override fun addQueryParameterForFindByIdentifier(uriBuilder: UriBuilder, id: String) {
        uriBuilder.queryParam("orderTypeCode", id)
    }

    override fun find(authenToken: AuthenticationToken, descptDefLang: String?, pageable: Pageable): Mono<Page<OrderType>> {
        return super.find(authenToken, pageable) { builder ->
            descptDefLang?.let { builder.queryParam("descptDefLang", it) }
        }
    }
}
