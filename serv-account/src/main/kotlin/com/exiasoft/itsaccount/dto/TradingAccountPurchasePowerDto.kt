package com.exiasoft.itsaccount.dto

import com.exiasoft.itscommon.serializer.BooleanDeserializer
import com.exiasoft.itscommon.serializer.BooleanSerializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.math.BigDecimal

data class TradingAccountPurchasePowerDto(

        var creditRiskCurrencyCode: String,

        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var applyBankIndicator: Boolean,

        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var applyCashIndicator: Boolean,

        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var applyCollateralIndicator: Boolean,

        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var applyCreditLineIndicator: Boolean,

        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var creditLineForAllStockIndicator: Boolean,

        var availableCreditLine: BigDecimal,

        var cashBalance: BigDecimal,

        var cashPurchasePower: BigDecimal,

        var holdAmount: BigDecimal,

        var netPurchasePower: BigDecimal,

        var totalBankAccountBalance: BigDecimal,

        var totalPurchasePower: BigDecimal,

        var ucaAmount: BigDecimal

) {
    constructor() : this(
            creditRiskCurrencyCode = "",
            applyBankIndicator = false, applyCashIndicator = false, applyCollateralIndicator = false, applyCreditLineIndicator = false, creditLineForAllStockIndicator = false,
            availableCreditLine = BigDecimal.ZERO, cashBalance = BigDecimal.ZERO,
            cashPurchasePower = BigDecimal.ZERO, holdAmount = BigDecimal.ZERO,
            netPurchasePower = BigDecimal.ZERO, totalBankAccountBalance = BigDecimal.ZERO,
            totalPurchasePower = BigDecimal.ZERO, ucaAmount = BigDecimal.ZERO
    )
}
