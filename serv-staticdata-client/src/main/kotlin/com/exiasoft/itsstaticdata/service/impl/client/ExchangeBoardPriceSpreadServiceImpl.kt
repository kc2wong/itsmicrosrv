package com.exiasoft.itsstaticdata.service.impl.client

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.service.client.BaseClientServiceImpl
import com.exiasoft.itsstaticdata.CONTEXT_PATH_INTERNAL
import com.exiasoft.itsstaticdata.model.ExchangeBoardPriceSpread
import com.exiasoft.itsstaticdata.service.ExchangeBoardPriceSpreadService
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono

class ExchangeBoardPriceSpreadServiceImpl(appName: String, loadBalancingClient: LoadBalancerClient, webClientBuilder: WebClient.Builder) :
        BaseClientServiceImpl<ExchangeBoardPriceSpread.Id, ExchangeBoardPriceSpread>(appName, "$CONTEXT_PATH_INTERNAL/sapi/price-spreads", loadBalancingClient, webClientBuilder), ExchangeBoardPriceSpreadService {

    override fun addQueryParameterForFindByIdentifier(uriBuilder: UriBuilder, id: ExchangeBoardPriceSpread.Id) {
        uriBuilder.queryParam("exchangeBoardOid", id.exchangeBoardOid)
                .queryParam("exchangeBoardPriceSpreadCode", id.exchangeBoardPriceSpreadCode)
    }

    override fun find(authenToken: AuthenticationToken, exchangeBoardOid: String?, exchangeBoardPriceSpreadCode: String?, pageable: Pageable): Mono<Page<ExchangeBoardPriceSpread>> {
        return super.find(authenToken, pageable) { builder ->
            exchangeBoardOid?.let { builder.queryParam("exchangeBoardOid", it) }
            exchangeBoardPriceSpreadCode?.let { builder.queryParam("exchangeBoardPriceSpreadCode", it) }
        }
    }

}