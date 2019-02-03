package com.exiasoft.itsorder.model

import com.exiasoft.itsorder.converter.BuySellConverter
import com.exiasoft.itsorder.converter.LotNatureConverter
import com.exiasoft.itsorder.model.enumeration.BuySell
import com.exiasoft.itsorder.model.enumeration.LotNature
import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamConverter
import java.math.BigDecimal

@XStreamAlias("ORDER")
data class OrderInputRequest (

        @XStreamAsAttribute
        @XStreamAlias("SIDE")
        @XStreamConverter(BuySellConverter::class)
        var buySell: BuySell,

        @XStreamAsAttribute
        @XStreamAlias("CACC_CODE")
        var tradingAccountCode: String,

        @XStreamAsAttribute
        @XStreamAlias("OPER_UNIT_CODE")
        var operationUnitCode: String,

        @XStreamAsAttribute
        @XStreamAlias("EXCHANGE_CODE")
        var exchangeCode: String,

        @XStreamAsAttribute
        @XStreamAlias("MARKET_INSTRUMENT_NO")
        var instrumentCode: String,

        @XStreamAsAttribute
        @XStreamAlias("INPUT_SOURCE")
        var channelCode: String,

        @XStreamAsAttribute
        @XStreamAlias("INSTRUCTION")
        var orderTypeCode: String,

        @XStreamAsAttribute
        @XStreamAlias("NATURE")
        @XStreamConverter(LotNatureConverter::class)
        var lotNature: LotNature,

        @XStreamAsAttribute
        @XStreamAlias("PRICE")
        var price: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("QUANTITY")
        var quantity: BigDecimal

) {
    constructor() : this(
            buySell = BuySell.Buy, tradingAccountCode = "", operationUnitCode = "",
            exchangeCode = "", instrumentCode = "", channelCode = "", orderTypeCode = "",
            lotNature = LotNature.Board, price = BigDecimal.ZERO, quantity = BigDecimal.ZERO
    )
}