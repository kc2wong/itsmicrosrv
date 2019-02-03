package com.exiasoft.itsorder.service

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itsorder.model.SimpleOrder
import com.exiasoft.itsorder.model.enumeration.OrderStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono
import java.time.LocalDate

interface SimpleOrderService {

    fun enquireOrder(authenToken: AuthenticationToken, tradingAccountCode: String, exchangeCode: String?,
                     startTradeDate: LocalDate?, endTradeDate: LocalDate?, orderStatus: Set<OrderStatus>, pageable: Pageable): Mono<Page<SimpleOrder>>
}