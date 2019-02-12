package com.exiasoft.itsorder.service.impl.server.demo

import com.exiasoft.itsauthen.service.DataEntitlementService
import com.exiasoft.itscommon.annotation.ItsFunction
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.bean.XStreamProvider
import com.exiasoft.itscommon.service.server.demo.BaseDemoServiceImpl
import com.exiasoft.itscommon.service.server.demo.BaseSimpleObjectDemoServiceImpl
import com.exiasoft.itsorder.model.SimpleOrder
import com.exiasoft.itsorder.model.SimpleOrderData
import com.exiasoft.itsorder.model.enumeration.OrderStatus
import com.exiasoft.itsorder.service.SimpleOrderService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalDate

@Service(value = "SimpleOrderServiceImpl")
class SimpleOrderServiceDemoImpl (
        tokenProvider: TokenProvider,
        xStreamProvider: XStreamProvider,
        val dataEntitlementService: DataEntitlementService
) : BaseSimpleObjectDemoServiceImpl<String, SimpleOrder, SimpleOrderData>(tokenProvider, xStreamProvider, SimpleOrderData::class.java), SimpleOrderService {

    override fun getResourceName(): String {
        return "/service/demo/order.xml"
    }

    override fun isEqualsInIdentifier(id: String, obj: SimpleOrder): Boolean {
        return id == obj.orderNumber
    }

    override fun getOid(obj: SimpleOrder): String {
        return obj.orderOid
    }

    @ItsFunction(["EnquiryGrp.Order.Enquiry"])
    override fun enquireOrder(authenToken: AuthenticationToken, tradingAccountCode: String, exchangeCode: String?, startTradeDate: LocalDate?, endTradeDate: LocalDate?, orderStatus: Set<OrderStatus>, pageable: Pageable): Mono<Page<SimpleOrder>> {
        var result = data.asSequence().toList()
        result = tradingAccountCode?.let { result.filter { e -> e.tradingAccountCode == it } } ?: result
        result = exchangeCode?.let { result.filter { e -> e.exchangeCode == it } } ?: result
        result = startTradeDate?.let { result.filter { e -> e.createTradeDate.compareTo(it) >= 0 } } ?: result
        result = endTradeDate?.let { result.filter { e -> e.createTradeDate.compareTo(it) <= 0 } } ?: result
        result = if (orderStatus.isNotEmpty()) result.filter { e -> orderStatus.contains(e.orderStatus) } else result
        result = if (pageable != Pageable.unpaged()) result.sortedWith(createComparator(pageable)) else result
        return Mono.just(getPage(authenToken, result, pageable))
    }

}

