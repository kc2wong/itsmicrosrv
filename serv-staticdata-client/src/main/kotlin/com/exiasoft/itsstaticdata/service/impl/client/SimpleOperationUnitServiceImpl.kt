package com.exiasoft.itsstaticdata.service.impl.client

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.service.client.BaseClientServiceImpl
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

class SimpleOperationUnitServiceImpl(appName: String, loadBalancingClient: LoadBalancerClient, webClientBuilder: WebClient.Builder) : SimpleOperationUnitService {

    private val delegate = SimpleOperationUnitServiceDataImpl(appName, loadBalancingClient, webClientBuilder)

    override fun findAll(authenToken: AuthenticationToken, pageable: Pageable): Mono<Page<SimpleOperationUnit>> {
        return delegate.findAll(authenToken, pageable).map {
            PageImpl(it.content.asSequence().map { ou -> ou as SimpleOperationUnit }.toList(), pageable, it.content.size.toLong())
        }
    }

    override fun findByIdentifier(authenToken: AuthenticationToken, id: String): Mono<SimpleOperationUnit> {
        return delegate.findByIdentifier(authenToken, id).map { it as SimpleOperationUnit }
    }

    override fun findByOid(authenToken: AuthenticationToken, oid: String): Mono<SimpleOperationUnit> {
        return delegate.findByOid(authenToken, oid).map { it as SimpleOperationUnit }
    }

    override fun findByOids(authenToken: AuthenticationToken, oids: Set<String>): Mono<Map<String, SimpleOperationUnit>> {
        return delegate.findByOids(authenToken, oids).map { it.mapValues { e -> e.value as SimpleOperationUnit } }
    }

    override fun find(authenToken: AuthenticationToken, nameOneDefLang: String?, pageable: Pageable): Mono<Page<SimpleOperationUnit>> {
        return delegate.find(authenToken, pageable) { builder ->
            nameOneDefLang?.let { builder.queryParam("nameOneDefLang", it) }
        }.map {
            PageImpl(it.content.asSequence().map { ou -> ou as SimpleOperationUnit }.toList(), pageable, it.content.size.toLong())
        }
    }

}

class SimpleOperationUnitServiceDataImpl(appName: String, loadBalancingClient: LoadBalancerClient, webClientBuilder: WebClient.Builder) :
        BaseClientServiceImpl<String, SimpleOperationUnitData>(appName,"$CONTEXT_PATH_INTERNAL/sapi/operation-units", loadBalancingClient, webClientBuilder) {

    override fun addQueryParameterForFindByIdentifier(uriBuilder: UriBuilder, id: String) {
        uriBuilder.queryParam("operationUnitCode", id)
    }

    fun find(authenToken: AuthenticationToken, nameOneDefLang: String?, pageable: Pageable): Mono<Page<SimpleOperationUnit>> {
        return super.find(authenToken, pageable) { builder ->
            nameOneDefLang?.let { builder.queryParam("nameOneDefLang", it) }
        }.map {
            PageImpl(it.content.asSequence().map { ou -> ou as SimpleOperationUnit }.toList(), pageable, it.content.size.toLong())
        }
    }

}
