package com.exiasoft.itsorder.model

import com.exiasoft.itsorder.converter.BuySellConverter
import com.exiasoft.itsorder.model.enumeration.BuySell
import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamConverter
import java.math.BigDecimal

@XStreamAlias("ORDER")
data class ChargeCommission(

        @XStreamAsAttribute
        @XStreamAlias("SIDE")
        @XStreamConverter(BuySellConverter::class)
        var buySell: BuySell,

        @XStreamAsAttribute
        @XStreamAlias("CACC_CODE")
        var tradingAccountCode: String,

        @XStreamAsAttribute
        @XStreamAlias("EXCHANGE_CODE")
        var exchangeCode: String,

        @XStreamAsAttribute
        @XStreamAlias("MARKET_INSTRUMENT_NO")
        var instrumentCode: String,

        @XStreamAsAttribute
        @XStreamAlias("PRICE")
        var price: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("QUANTITY")
        var quantity: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("CHARGE_AMT")
        var chargeAmount: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("COMM_AMT")
        var commissionAmount: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("GROSS_CONSIDERATION")
        var grossAmount: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("NET_AMOUNT")
        var netAmount: BigDecimal

) {
    constructor() : this(
            buySell = BuySell.Buy, tradingAccountCode = "", exchangeCode = "",
            instrumentCode = "", price = BigDecimal.ZERO, quantity = BigDecimal.ZERO,
            chargeAmount = BigDecimal.ZERO, commissionAmount = BigDecimal.ZERO,
            grossAmount = BigDecimal.ZERO, netAmount = BigDecimal.ZERO
    )
}