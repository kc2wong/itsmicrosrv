package com.exiasoft.itsaccount.dto

import java.math.BigDecimal

data class AccountPortfolioDto(

        var tradingAccountCode: String,

        var currencyCode: String,

        var totalAvailableBalance: BigDecimal,

        var totalAvailableBalanceBaseCurrency: BigDecimal,

        var totalBalanceAmount: BigDecimal,

        var totalBalanceAmountBaseCurrency: BigDecimal,

        var totalDuePayBaseCurrency: BigDecimal,

        var totalDueReceiveBaseCurrency: BigDecimal,

        var totalOverduePayBaseCurrency: BigDecimal,

        var totalOverdueReceiveBaseCurrency: BigDecimal,

        var totalUnderduePayBaseCurrency: BigDecimal,

        var totalUnderdueReceiveBaseCurrency: BigDecimal,

        var securityPositionSummary: List<SecurityPositionSummaryDto>,

        var cashPortfolio: List<CashPortfolioDto>,

        var purchasePower: TradingAccountPurchasePowerDto?

) {
    constructor() : this(
            tradingAccountCode = "", currencyCode = "",
            totalAvailableBalance = BigDecimal.ZERO, totalAvailableBalanceBaseCurrency = BigDecimal.ZERO,
            totalBalanceAmount = BigDecimal.ZERO, totalBalanceAmountBaseCurrency = BigDecimal.ZERO,
            totalDuePayBaseCurrency = BigDecimal.ZERO, totalDueReceiveBaseCurrency = BigDecimal.ZERO,
            totalOverduePayBaseCurrency = BigDecimal.ZERO, totalOverdueReceiveBaseCurrency = BigDecimal.ZERO,
            totalUnderduePayBaseCurrency = BigDecimal.ZERO, totalUnderdueReceiveBaseCurrency = BigDecimal.ZERO,
            securityPositionSummary = emptyList(), cashPortfolio = emptyList(), purchasePower = null
    )
}