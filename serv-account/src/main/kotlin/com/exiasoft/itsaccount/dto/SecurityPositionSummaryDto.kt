package com.exiasoft.itsaccount.dto

import java.math.BigDecimal

data class SecurityPositionSummaryDto(

        var exchangeCode: String,

        var instrumentCode: String,

        var currencyCode: String,

        var averagePrice: BigDecimal,

        var closingPrice: BigDecimal,

        var availableQuantity: BigDecimal,

        var underPayQuantity: BigDecimal,

        var underReceiveQuantity: BigDecimal,

        var duePayQuantity: BigDecimal,

        var dueReceiveQuantity: BigDecimal,

        var overduePayQuantity: BigDecimal,

        var overdueReceiveQuantity: BigDecimal,

        var holdQuantity: BigDecimal,

        var netBalance: BigDecimal,

        var outstandingMasterOrderQuantity: BigDecimal,

        var outstandingOrderQuantity: BigDecimal,

        var sellableQuantity: BigDecimal,

        var stockHolding: BigDecimal,

        var totalQuantity: BigDecimal,

        var unavailableHoldQuantity: BigDecimal,

        var unavailableQuantity: BigDecimal,

        var marketValue: BigDecimal,

        var marketValueBaseCurrency: BigDecimal

) {
    constructor() : this(
            exchangeCode = "", instrumentCode = "", currencyCode = "",
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