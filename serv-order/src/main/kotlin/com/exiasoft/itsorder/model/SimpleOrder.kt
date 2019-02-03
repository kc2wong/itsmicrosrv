package com.exiasoft.itsorder.model

import com.exiasoft.itsorder.model.enumeration.BuySell
import com.exiasoft.itsorder.model.enumeration.OrderStatus
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

interface SimpleOrder {
    var orderOid: String
    var orderNumber: String
    var buySell: BuySell
    var tradingAccountCode: String
    var exchangeCode: String
    var instrumentOid: String
    var instrumentCode: String
    var orderChannelCode: String?
    var price: BigDecimal
    var quantity: BigDecimal
    var executedQuantity: BigDecimal
    var chargeAmount: BigDecimal
    var commissionAmount: BigDecimal
    var grossAmount: BigDecimal
    var netAmount: BigDecimal
    var executedAmount: BigDecimal
    var orderStatus: OrderStatus
    var createDateTime: LocalDateTime
    var createTradeDate: LocalDate
    var updateDateTime: LocalDateTime
    var updateTradeDate: LocalDate
    var rejectReason: String?
}