package com.exiasoft.itsorder.dto

import com.exiasoft.itscommon.serializer.LocalDateDeserializer
import com.exiasoft.itscommon.serializer.LocalDateSerializer
import com.exiasoft.itscommon.serializer.LocalDateTimeDeserializer
import com.exiasoft.itscommon.serializer.LocalDateTimeSerializer
import com.exiasoft.itscommon.util.DateTimeUtil
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

data class OrderExecutionDto(

        var price: BigDecimal,

        var quantity: BigDecimal,

        var sellerBroker: String?,

        var buyerBroker: String?,

        var splitIndicator: Boolean,

        var voidIndicator: Boolean,

        @JsonSerialize(using = LocalDateSerializer::class)
        @JsonDeserialize(using = LocalDateDeserializer::class)
        var executeTradeDate: LocalDate,

        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        var executeDateTime: LocalDateTime,

        var source: String?

) {
    constructor() : this(
            price = BigDecimal.ZERO, quantity = BigDecimal.ZERO, sellerBroker = null, buyerBroker = null,
            splitIndicator = false, voidIndicator = false,
            executeDateTime = DateTimeUtil.getCurrentDateTime(), executeTradeDate = DateTimeUtil.getCurrentDate(),
            source = null
    )
}