package com.exiasoft.itsaccount.model


import com.exiasoft.itsaccount.converter.AccountPortfolioConverter
import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamConverter
import com.thoughtworks.xstream.annotations.XStreamImplicit
import java.math.BigDecimal

@XStreamAlias("CACC_PORTFOLIO")
@XStreamConverter(AccountPortfolioConverter::class)
data class AccountPortfolio(

        @XStreamAsAttribute
        @XStreamAlias("CACC_CODE")
        var tradingAccountCode: String,

        @XStreamAsAttribute
        @XStreamAlias("CCY_CODE")
        var currencyCode: String,

        @XStreamAsAttribute
        @XStreamAlias("TTL_AVAIL_BAL")
        var totalAvailableBalance: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("TTL_AVAIL_BAL_BASE_CCY")
        var totalAvailableBalanceBaseCurrency: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("TTL_BAL_AMT")
        var totalBalanceAmount: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("TTL_BAL_AMT_BASE_CCY")
        var totalBalanceAmountBaseCurrency: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("TTL_DUE_PAY_BASE_CCY")
        var totalDuePayBaseCurrency: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("TTL_DUE_RECEIVE_BASE_CCY")
        var totalDueReceiveBaseCurrency: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("TTL_OVERDUE_PAY_BASE_CCY")
        var totalOverduePayBaseCurrency: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("TTL_OVERDUE_RECEIVE_BASE_CCY")
        var totalOverdueReceiveBaseCurrency: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("TTL_UNDERDUE_PAY_BASE_CCY")
        var totalunderduePayBaseCurrency: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("TTL_UNDERDUE_RECEIVE_BASE_CCY")
        var totalunderdueReceiveBaseCurrency: BigDecimal,

        @XStreamImplicit
        var securityPositionSummary: List<SecurityPositionSummary>,

        @XStreamImplicit
        var cashPortfolio: List<CashPortfolio>,

        @XStreamAlias("CACC_PURCHASING_POWER")
        var purchasePower: TradingAccountPurchasePower?
) {
    constructor() : this(
            tradingAccountCode = "", currencyCode = "",
            totalAvailableBalance = BigDecimal.ZERO, totalAvailableBalanceBaseCurrency = BigDecimal.ZERO,
            totalBalanceAmount = BigDecimal.ZERO, totalBalanceAmountBaseCurrency = BigDecimal.ZERO,
            totalDuePayBaseCurrency = BigDecimal.ZERO, totalDueReceiveBaseCurrency = BigDecimal.ZERO,
            totalOverduePayBaseCurrency = BigDecimal.ZERO, totalOverdueReceiveBaseCurrency = BigDecimal.ZERO,
            totalunderduePayBaseCurrency = BigDecimal.ZERO, totalunderdueReceiveBaseCurrency = BigDecimal.ZERO,
            securityPositionSummary = emptyList(), cashPortfolio = emptyList(), purchasePower = null
    )

    data class AccountPortfolioId(var tradingAccountCode: String, var currencyCode: String) {
    }
}

