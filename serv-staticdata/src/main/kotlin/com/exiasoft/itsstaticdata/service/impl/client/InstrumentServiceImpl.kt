package com.exiasoft.itsstaticdata.service.impl.client

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.model.enumaration.Status
import com.exiasoft.itscommon.service.client.BaseClientServiceImpl
import com.exiasoft.itsstaticdata.CONTEXT_PATH_INTERNAL
import com.exiasoft.itsstaticdata.model.Instrument
import com.exiasoft.itsstaticdata.model.SimpleStock
import com.exiasoft.itsstaticdata.service.SimpleStockService
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono

class SimpleStockServiceImpl(appName: String, loadBalancingClient: LoadBalancerClient, webClientBuilder: WebClient.Builder) :
        BaseClientServiceImpl<Instrument.Id, SimpleStock>(appName, "$CONTEXT_PATH_INTERNAL/sapi/instruments", loadBalancingClient, webClientBuilder), SimpleStockService {

    override fun addQueryParameterForFindByIdentifier(uriBuilder: UriBuilder, id: Instrument.Id) {
        uriBuilder.queryParam("exchangeBoardOid", id.exchangeBoardOid)
                .queryParam("instrumentCode", id.instrumentCode)
    }

    override fun find(authenToken: AuthenticationToken, exchangeBoardOid: Set<String>?, instrumentCode: String?, nameDefLang: String?, status: Status?, pageable: Pageable): Mono<Page<SimpleStock>> {
        return super.find(authenToken, pageable) { builder ->
            exchangeBoardOid?.let { addQueryParameter(builder, "exchangeBoardOid", it) }
            instrumentCode?.let { builder.queryParam("instrumentCode", it) }
            nameDefLang?.let { builder.queryParam("nameDefLang", it) }
            status?.let { builder.queryParam("status", it.value) }
        }
    }

}
