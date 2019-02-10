package com.exiasoft.itsaccount.dto

import java.math.BigDecimal

data class CashPortfolioDto(

        var currencyCode: String,

        var availableBalance: BigDecimal,

        var availableBalanceBaseCcy: BigDecimal,

        var balanceAmount: BigDecimal,

        var balanceAmountBaseCcy: BigDecimal,

        var cashHolding: BigDecimal,

        var cashHoldingBaseCcy: BigDecimal,

        var duePayAmount: BigDecimal,

        var dueReceiveAmount: BigDecimal,

        var overduePayAmount: BigDecimal,

        var overdueReceiveAmount: BigDecimal,

        var underduePayAmount: BigDecimal,

        var underdueReceiveAmount: BigDecimal,

        var holdAmount: BigDecimal,

        var netAmount: BigDecimal,

        var interestPayAmount: BigDecimal,

        var interestReceiveAmount: BigDecimal,

        var settledAccountBalance: BigDecimal,

        var unavailableAmount: BigDecimal

) {
    constructor() : this(
            currencyCode = "",
            availableBalance = BigDecimal.ZERO, availableBalanceBaseCcy = BigDecimal.ZERO, balanceAmount = BigDecimal.ZERO, balanceAmountBaseCcy = BigDecimal.ZERO,
            cashHolding = BigDecimal.ZERO, cashHoldingBaseCcy = BigDecimal.ZERO, holdAmount = BigDecimal.ZERO, netAmount = BigDecimal.ZERO,
            duePayAmount = BigDecimal.ZERO, dueReceiveAmount = BigDecimal.ZERO, underduePayAmount = BigDecimal.ZERO, underdueReceiveAmount = BigDecimal.ZERO, overduePayAmount = BigDecimal.ZERO, overdueReceiveAmount = BigDecimal.ZERO,
            interestPayAmount = BigDecimal.ZERO, interestReceiveAmount = BigDecimal.ZERO, settledAccountBalance = BigDecimal.ZERO, unavailableAmount = BigDecimal.ZERO
    )
}