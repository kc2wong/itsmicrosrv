package com.exiasoft.itsorder.dto

import com.exiasoft.itscommon.serializer.LocalDateTimeDeserializer
import com.exiasoft.itscommon.serializer.LocalDateTimeSerializer
import com.exiasoft.itscommon.util.DateTimeUtil
import com.exiasoft.itsorder.model.enumeration.BuySell
import com.exiasoft.itsorder.model.enumeration.OrderStatus
import com.exiasoft.itsorder.serializer.BuySellDeserializer
import com.exiasoft.itsorder.serializer.OrderStatusDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.math.BigDecimal
import java.time.LocalDateTime

data class SimpleOrderDto(
        var orderNumber: String,

        @JsonDeserialize(using = BuySellDeserializer::class)
        var buySell: BuySell,

        var tradingAccountCode: String,

        var exchangeCode: String,

        var instrumentCode: String,

        var price: BigDecimal,

        var quantity: BigDecimal,

        var chargeAmount: BigDecimal,

        var commissionAmount: BigDecimal,

        var grossAmount: BigDecimal,

        var netAmount: BigDecimal,

        var executedAmount: BigDecimal,

        var executedQuantity: BigDecimal,

        @JsonDeserialize(using = OrderStatusDeserializer::class)
        var orderStatus: OrderStatus,

        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        var createDateTime: LocalDateTime,

        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        var createTradeDate: LocalDateTime,

        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        var updateDateTime: LocalDateTime,

        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        var updateTradeDate: LocalDateTime,

        var orderChannelCode: String?,

        var rejectReason: String?

) {
    constructor() : this(
            orderNumber = "", buySell = BuySell.Buy, tradingAccountCode = "", exchangeCode = "",
            instrumentCode = "", price = BigDecimal.ZERO, quantity = BigDecimal.ZERO,
            chargeAmount = BigDecimal.ZERO, commissionAmount = BigDecimal.ZERO, grossAmount = BigDecimal.ZERO, netAmount = BigDecimal.ZERO,
            executedQuantity = BigDecimal.ZERO, executedAmount = BigDecimal.ZERO, orderStatus = OrderStatus.Pending,
            createDateTime = DateTimeUtil.getCurrentDateTime(), createTradeDate = DateTimeUtil.getCurrentDateTime(),
            updateDateTime = DateTimeUtil.getCurrentDateTime(), updateTradeDate = DateTimeUtil.getCurrentDateTime(),
            rejectReason = null, orderChannelCode = null
    )
}
