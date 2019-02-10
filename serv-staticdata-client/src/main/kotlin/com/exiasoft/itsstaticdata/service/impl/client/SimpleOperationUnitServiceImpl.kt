package com.exiasoft.itsstaticdata.service.impl.client

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.service.client.BaseSimpleObjectClientServiceImpl
import com.exiasoft.itsstaticdata.CONTEXT_PATH_INTERNAL
import com.exiasoft.itsstaticdata.model.SimpleOperationUnit
import com.exiasoft.itsstaticdata.model.SimpleOperationUnitData
import com.exiasoft.itsstaticdata.service.SimpleOperationUnitService
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono

class SimpleOperationUnitServiceImpl(appName: String, loadBalancingClient: LoadBalancerClient, webClientBuilder: WebClient.Builder) :
        BaseSimpleObjectClientServiceImpl<String, SimpleOperationUnit, SimpleOperationUnitData>(appName, "$CONTEXT_PATH_INTERNAL/sapi/operation-units", loadBalancingClient, webClientBuilder),
        SimpleOperationUnitService {

    override fun addQueryParameterForFindByIdentifier(uriBuilder: UriBuilder, id: String) {
        uriBuilder.queryParam("operationUnitCode", id)
    }

    override fun find(authenToken: AuthenticationToken, nameOneDefLang: String?, pageable: Pageable): Mono<Page<SimpleOperationUnit>> {
        return super.find(authenToken, pageable) { builder ->
            nameOneDefLang?.let { builder.queryParam("nameOneDefLang", it) }
        }.map {
            PageImpl(it.content.asSequence().map { ou -> ou as SimpleOperationUnit }.toList(), pageable, it.content.size.toLong())
        }
    }

}
