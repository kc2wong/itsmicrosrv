package com.exiasoft.itsaccount.model

import com.exiasoft.itscommon.converter.BooleanConverter
import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamConverter
import java.math.BigDecimal

@XStreamAlias("CACC_PURCHASING_POWER")
data class TradingAccountPurchasePower(

        @XStreamAsAttribute @XStreamAlias("CREDIT_RISK_CCY_OID")
        var creditRiskCurrencyOid: String,

        @XStreamAsAttribute @XStreamAlias("APPLY_BANK_IND")
        @XStreamConverter(BooleanConverter::class)
        var applyBankIndicator: Boolean,

        @XStreamAsAttribute @XStreamAlias("APPLY_CASH_IND")
        @XStreamConverter(BooleanConverter::class)
        var applyCashIndicator: Boolean,

        @XStreamAsAttribute @XStreamAlias("APPLY_COLLATERAL_LINE_IND")
        @XStreamConverter(BooleanConverter::class)
        var applyCollateralIndicator: Boolean,

        @XStreamAsAttribute @XStreamAlias("APPLY_CREDIT_LINE_IND")
        @XStreamConverter(BooleanConverter::class)
        var applyCreditLineIndicator: Boolean,

        var creditLineForAllStockIndicator: Boolean,

        @XStreamAsAttribute
        @XStreamAlias("AVAILBLE_CREDIT_LINE")
        var availableCreditLine: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("CASH_BAL")
        var cashBalance: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("CASH_PURCHASE_POWER")
        var cashPurchasePower: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("HOLD_AMT")
        var holdAmount: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("NET_PURCHASE_POWER")
        var netPurchasePower: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("TTL_BANK_ACCT_BAL")
        var totalBankAccountBalance: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("TTL_PURCHASE_POWER")
        var totalPurchasePower: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("UCA_AMT")
        var ucaAmount: BigDecimal

) {
    constructor() : this(
            creditRiskCurrencyOid = "",
            applyBankIndicator = false, applyCashIndicator = false, applyCollateralIndicator = false, applyCreditLineIndicator = false, creditLineForAllStockIndicator = false,
            availableCreditLine = BigDecimal.ZERO, cashBalance = BigDecimal.ZERO,
            cashPurchasePower = BigDecimal.ZERO, holdAmount = BigDecimal.ZERO,
            netPurchasePower = BigDecimal.ZERO, totalBankAccountBalance = BigDecimal.ZERO,
            totalPurchasePower = BigDecimal.ZERO, ucaAmount = BigDecimal.ZERO
    )
}