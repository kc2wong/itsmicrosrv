package com.exiasoft.itsorder.service

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itsorder.model.ChargeCommission
import com.exiasoft.itsorder.model.Order
import com.exiasoft.itsorder.model.OrderCancelRequest
import com.exiasoft.itsorder.model.OrderInputRequest
import reactor.core.publisher.Mono

interface OrderService {

    fun calculateChargeCommission(authenToken: AuthenticationToken, orderInputRequest: OrderInputRequest): Mono<ChargeCommission>

    fun newOrder(authenToken: AuthenticationToken, orderInputRequest: OrderInputRequest): Mono<Order>

    fun cancelOrder(authenToken: AuthenticationToken, orderCancelRequest: OrderCancelRequest): Mono<Order>

    fun findByIdentifier(authenToken: AuthenticationToken, orderNumber: String): Mono<Order>
}