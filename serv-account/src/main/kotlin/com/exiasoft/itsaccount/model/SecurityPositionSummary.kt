package com.exiasoft.itsaccount.model


import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import java.math.BigDecimal

@XStreamAlias("CACC_SECURITIES_POS_SUM")
data class SecurityPositionSummary(

        @XStreamAsAttribute
        @XStreamAlias("CACC_SECURITIES_POS_SUM_OID")
        var securityPositionSummaryOid: String,

        @XStreamAsAttribute
        @XStreamAlias("CACC_OID")
        var tradingAccountOid: String,

        @XStreamAsAttribute
        @XStreamAlias("EXCH_OID")
        var exchangeOid: String,

        @XStreamAsAttribute
        @XStreamAlias("MARKET_INSTRUMENT_OID")
        var instrumentOid: String,

        @XStreamAsAttribute
        @XStreamAlias("MARKET_INSTRUMENT_NO")
        var instrumentCode: String,

        @XStreamAsAttribute
        @XStreamAlias("PRODUCT_TYPE_OID")
        var productTypeOid: String,

        @XStreamAsAttribute
        @XStreamAlias("CCY_OID")
        var currencyOid: String,

        @XStreamAsAttribute
        @XStreamAlias("AVERAGE_PRICE")
        var averagePrice: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("CLOSING_PRICE")
        var closingPrice: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("CURR_AVAIL_QTY")
        var availableQuantity: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("UNDERDUE_PAY_QTY")
        var underPayQuantity: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("UNDERDUE_RECEIVE_QTY")
        var underReceiveQuantity: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("DUE_PAY_QTY")
        var duePayQuantity: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("DUE_RECEIVE_QTY")
        var dueReceiveQuantity: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("OVERDUE_PAY_QTY")
        var overduePayQuantity: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("OVERDUE_RECEIVE_QTY")
        var overdueReceiveQuantity: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("HOLD_QTY")
        var holdQuantity: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("NET_BALANCE")
        var netBalance: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("OS_MASTER_ORDER_QTY")
        var outstandingMasterOrderQuantity: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("OS_ORDER_QTY")
        var outstandingOrderQuantity: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("SELLABLE_QTY")
        var sellableQuantity: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("STOCK_HOLDING")
        var stockHolding: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("TOTAL_QTY")
        var totalQuantity: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("UNAVAILABLE_HOLD_QTY")
        var unavailableHoldQuantity: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("UNAVAILABLE_QTY")
        var unavailableQuantity: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("MKT_VAL")
        var marketValue: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("MKT_VAL_BASE_CCY")
        var marketValueBaseCurrency: BigDecimal

) {
    constructor() : this(
            securityPositionSummaryOid = "", tradingAccountOid = "",
            exchangeOid = "", instrumentOid = "", instrumentCode = "", productTypeOid = "", currencyOid = "",
            averagePrice = BigDecimal.ZERO, closingPrice = BigDecimal.ZERO,
            availableQuantity = BigDecimal.ZERO,
            underPayQuantity = BigDecimal.ZERO, underReceiveQuantity = BigDecimal.ZERO,
            duePayQuantity = BigDecimal.ZERO, dueReceiveQuantity = BigDecimal.ZERO,
            overduePayQuantity = BigDecimal.ZERO, overdueReceiveQuantity = BigDecimal.ZERO,
            holdQuantity = BigDecimal.ZERO, netBalance = BigDecimal.ZERO, outstandingMasterOrderQuantity = BigDecimal.ZERO, outstandingOrderQuantity = BigDecimal.ZERO,
            sellableQuantity = BigDecimal.ZERO, stockHolding = BigDecimal.ZERO,
            totalQuantity = BigDecimal.ZERO, unavailableHoldQuantity = BigDecimal.ZERO, unavailableQuantity = BigDecimal.ZERO,
            marketValue = BigDecimal.ZERO, marketValueBaseCurrency = BigDecimal.ZERO
    )
}