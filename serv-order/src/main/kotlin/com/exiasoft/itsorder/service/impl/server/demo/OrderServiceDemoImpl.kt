package com.exiasoft.itsorder.service.impl.server.demo

import com.exiasoft.itsauthen.service.DataEntitlementService
import com.exiasoft.itscommon.annotation.ItsFunction
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.bean.XStreamProvider
import com.exiasoft.itscommon.service.server.demo.BaseDemoServiceImpl
import com.exiasoft.itsorder.model.*
import com.exiasoft.itsorder.model.enumeration.BuySell
import com.exiasoft.itsorder.service.OrderService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.math.BigDecimal

@Service(value = "OrderServiceDemoImpl")
@ItsFunction(["EnquiryGrp.Order.Enquiry"])
class OrderServiceDemoImpl (
        tokenProvider: TokenProvider,
        xStreamProvider: XStreamProvider,
        val dataEntitlementService: DataEntitlementService
) : BaseDemoServiceImpl<String, Order>(tokenProvider, xStreamProvider, Order::class.java, listOf(OrderExecution::class.java)), OrderService {

    override fun getOid(obj: Order): String {
        return obj.orderOid
    }

    override fun getResourceName(): String {
        return "/service/demo/order.xml"
    }

    override fun isEqualsInIdentifier(id: String, obj: Order): Boolean {
        return id == obj.orderNumber
    }

    @ItsFunction(["OrderGrp.Order.Capture"])
    override fun calculateChargeCommission(authenToken: AuthenticationToken, orderInputRequest: OrderInputRequest): Mono<ChargeCommission> {
        return orderInputRequest?.let {
            val grossAmt = it.price.multiply(it.quantity)
            val commissionAmt = grossAmt.multiply(BigDecimal("0.25")).movePointRight(-2)
            val chargeAmt = grossAmt.multiply(BigDecimal("0.1")).movePointRight(-2)
            val chargeCommissionAmt = commissionAmt.add(chargeAmt)
            Mono.just(ChargeCommission(it.buySell, it.tradingAccountCode, it.exchangeCode, it.instrumentCode, it.price, it.quantity, chargeAmt, commissionAmt, grossAmt,
                    if (it.buySell == BuySell.Buy) grossAmt.add(chargeCommissionAmt) else grossAmt.subtract(chargeCommissionAmt)))
        }
    }

    @ItsFunction(["OrderGrp.Order.Capture"])
    override fun newOrder(authenToken: AuthenticationToken, orderInputRequest: OrderInputRequest): Mono<Order> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @ItsFunction(["OrderGrp.Order.Cancel"])
    override fun cancelOrder(authenToken: AuthenticationToken, orderCancelRequest: OrderCancelRequest): Mono<Order> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}