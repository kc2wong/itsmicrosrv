package com.exiasoft.itsstaticdata.service.impl.client

import com.exiasoft.itscommon.service.client.BaseClientServiceImpl
import com.exiasoft.itsstaticdata.CONTEXT_PATH_INTERNAL
import com.exiasoft.itsstaticdata.model.OrderChannel
import com.exiasoft.itsstaticdata.service.OrderChannelService
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder

class OrderChannelServiceImpl(appName: String, loadBalancingClient: LoadBalancerClient, webClientBuilder: WebClient.Builder) :
        BaseClientServiceImpl<String, OrderChannel>(appName,"$CONTEXT_PATH_INTERNAL/sapi/order-channels", loadBalancingClient, webClientBuilder), OrderChannelService {

    override fun addQueryParameterForFindByIdentifier(uriBuilder: UriBuilder, id: String) {
        uriBuilder.queryParam("orderChannelCode", id)
    }

}
