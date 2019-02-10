package com.exiasoft.itsorder.dto

import com.exiasoft.itsorder.model.enumeration.BuySell
import com.exiasoft.itsorder.serializer.BuySellDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import java.math.BigDecimal

data class ChargeCommissionDto(

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

        var netAmount: BigDecimal
) {
    constructor() : this(
            buySell = BuySell.Buy, tradingAccountCode = "", exchangeCode = "",
            instrumentCode = "", price = BigDecimal.ZERO, quantity = BigDecimal.ZERO,
            chargeAmount = BigDecimal.ZERO, commissionAmount = BigDecimal.ZERO, grossAmount = BigDecimal.ZERO, netAmount = BigDecimal.ZERO
    )
}