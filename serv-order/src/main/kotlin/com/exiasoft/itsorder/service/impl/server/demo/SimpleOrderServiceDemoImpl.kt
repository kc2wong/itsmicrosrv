package com.exiasoft.itsorder.service.impl.server.demo

import com.exiasoft.itsauthen.service.DataEntitlementService
import com.exiasoft.itscommon.annotation.ItsFunction
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.bean.XStreamProvider
import com.exiasoft.itscommon.service.server.demo.BaseDemoServiceImpl
import com.exiasoft.itsorder.model.SimpleOrder
import com.exiasoft.itsorder.model.SimpleOrderData
import com.exiasoft.itsorder.model.enumeration.OrderStatus
import com.exiasoft.itsorder.service.SimpleOrderService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalDate

@Service(value = "SimpleOrderServiceImpl")
class SimpleOrderServiceDemoImpl (
        tokenProvider: TokenProvider,
        xStreamProvider: XStreamProvider,
        val dataEntitlementService: DataEntitlementService
) : SimpleOrderService {

    val delegate = SimpleOrderServiceDataDemoImpl(tokenProvider, xStreamProvider, dataEntitlementService)

    override fun enquireOrder(authenToken: AuthenticationToken, tradingAccountCode: String, exchangeCode: String?, startTradeDate: LocalDate?, endTradeDate: LocalDate?, orderStatus: Set<OrderStatus>, pageable: Pageable): Mono<Page<SimpleOrder>> {
        return delegate.enquireOrder(authenToken, tradingAccountCode, exchangeCode, startTradeDate, endTradeDate, orderStatus, pageable)
                .map { PageImpl(it.content.asSequence().map { it as SimpleOrder }.toList(), pageable, it.totalElements) }

    }

    class SimpleOrderServiceDataDemoImpl (
            tokenProvider: TokenProvider,
            xStreamProvider: XStreamProvider,
            val dataEntitlementService: DataEntitlementService
    ) : BaseDemoServiceImpl<String, SimpleOrderData>(tokenProvider, xStreamProvider, SimpleOrderData::class.java) {

        override fun getOid(obj: SimpleOrderData): String {
            return obj.orderOid
        }

        override fun getResourceName(): String {
            return "/service/demo/order.xml"
        }

        override fun isEqualsInIdentifier(id: String, obj: SimpleOrderData): Boolean {
            return id == obj.orderNumber
        }

        @ItsFunction(["EnquiryGrp.Order.Enquiry"])
        fun enquireOrder(authenToken: AuthenticationToken, tradingAccountCode: String, exchangeCode: String?, startTradeDate: LocalDate?, endTradeDate: LocalDate?, orderStatus: Set<OrderStatus>, pageable: Pageable): Mono<Page<SimpleOrderData>> {
            var result = data.asSequence().toList()
            result = tradingAccountCode?.let { result.filter { e -> e.tradingAccountCode == it } } ?: result
            result = exchangeCode?.let { result.filter { e -> e.exchangeCode == it } } ?: result
            result = startTradeDate?.let { result.filter { e -> e.createTradeDate.compareTo(it) >= 0 } } ?: result
            result = endTradeDate?.let { result.filter { e -> e.createTradeDate.compareTo(it) <= 0 } } ?: result
            result = if (orderStatus.isNotEmpty()) result.filter { e -> orderStatus.contains(e.orderStatus) } else result
            return Mono.just(getPage(authenToken, result, pageable))
        }
    }
}

