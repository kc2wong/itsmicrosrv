package com.exiasoft.itsaccount.model

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import java.math.BigDecimal

@XStreamAlias("CACC_CASH_PORTFOLIO")
data class CashPortfolio(

        @XStreamAsAttribute
        @XStreamAlias("CCY_OID")
        var currencyOid: String,

        @XStreamAsAttribute
        @XStreamAlias("CCY_CODE")
        var currencyCode: String,

        @XStreamAsAttribute
        @XStreamAlias("AVAIL_BAL")
        var availableBalance: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("AVAIL_BAL_BASE_CCY")
        var availableBalanceBaseCcy: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("BAL_AMT")
        var balanceAmount: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("BAL_AMT_BASE_CCY")
        var balanceAmountBaseCcy: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("CASH_HOLDING")
        var cashHolding: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("CASH_HOLDING_BASE_CCY")
        var cashHoldingBaseCcy: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("DUE_PAY_AMT")
        var duePayAmount: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("DUE_RECEIVE_AMT")
        var dueReceiveAmount: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("OVERDUE_PAY_AMT")
        var overduePayAmount: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("OVERDUE_RECEIVE_AMT")
        var overdueReceiveAmount: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("UNDERDUE_PAY_AMT")
        var underduePayAmount: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("UNDERDUE_RECEIVE_AMT")
        var underdueReceiveAmount: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("HOLD_AMT")
        var holdAmount: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("NET_AMT")
        var netAmount: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("INTEREST_PAY_AMT")
        var interestPayAmount: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("INTEREST_RECEIVE_AMT")
        var interestReceiveAmount: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("SETTLED_ACCT_BAL")
        var settledAccountBalance: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("UNAVAILABLE_AMT")
        var unavailableAmount: BigDecimal

) {
    constructor() : this(
            currencyOid = "", currencyCode = "",
            availableBalance = BigDecimal.ZERO, availableBalanceBaseCcy = BigDecimal.ZERO, balanceAmount = BigDecimal.ZERO, balanceAmountBaseCcy = BigDecimal.ZERO,
            cashHolding = BigDecimal.ZERO, cashHoldingBaseCcy = BigDecimal.ZERO, holdAmount = BigDecimal.ZERO, netAmount = BigDecimal.ZERO,
            duePayAmount = BigDecimal.ZERO, dueReceiveAmount = BigDecimal.ZERO, underduePayAmount = BigDecimal.ZERO, underdueReceiveAmount = BigDecimal.ZERO, overduePayAmount = BigDecimal.ZERO, overdueReceiveAmount = BigDecimal.ZERO,
            interestPayAmount = BigDecimal.ZERO, interestReceiveAmount = BigDecimal.ZERO, settledAccountBalance = BigDecimal.ZERO, unavailableAmount = BigDecimal.ZERO
    )
}