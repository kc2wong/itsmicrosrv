package com.exiasoft.itsorder.dto

import com.exiasoft.itsorder.model.enumeration.BuySell
import com.exiasoft.itsorder.model.enumeration.LotNature
import com.exiasoft.itsorder.serializer.BuySellDeserializer
import com.exiasoft.itsorder.serializer.LotNatureDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import java.math.BigDecimal

data class OrderInputRequestDto(

        @JsonDeserialize(using = BuySellDeserializer::class)
        var buySell: BuySell,

        var tradingAccountCode: String,

        var operationUnitCode: String,

        var exchangeCode: String,

        var instrumentCode: String,

        var channelCode: String,

        var orderTypeCode: String,

        @JsonDeserialize(using = LotNatureDeserializer::class)
        var lotNature: LotNature,

        var price: BigDecimal,

        var quantity: BigDecimal

) {
    constructor() : this(
            buySell = BuySell.Buy, tradingAccountCode = "", operationUnitCode = "",
            exchangeCode = "", instrumentCode = "", channelCode = "", orderTypeCode = "",
            lotNature = LotNature.Board, price = BigDecimal.ZERO, quantity = BigDecimal.ZERO
    )
}