package com.exiasoft.itsorder.model

import com.exiasoft.itscommon.converter.BooleanConverter
import com.exiasoft.itscommon.converter.LocalDateConverter
import com.exiasoft.itscommon.converter.LocalDateTimeConverter
import com.exiasoft.itscommon.util.DateTimeUtil
import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamConverter
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@XStreamAlias("EXECUTED_ORDER")
data class OrderExecution(

        @XStreamAsAttribute
        @XStreamAlias("EXECUTED_ORDER_OID")
        var executedOrderOid: String,

        @XStreamAsAttribute
        @XStreamAlias("ORDER_OID")
        var orderOid: String,

        @XStreamAsAttribute
        @XStreamAlias("PRICE")
        var price: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("QUANTITY")
        var quantity: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("SELLER_BROKER")
        var sellerBroker: String?,

        @XStreamAsAttribute
        @XStreamAlias("BUYER_BROKER")
        var buyerBroker: String?,

        @XStreamAsAttribute
        @XStreamAlias("HAS_SPLIT_IND")
        @XStreamConverter(BooleanConverter::class)
        var splitIndicator: Boolean,

        @XStreamAsAttribute
        @XStreamAlias("VOID_IND")
        @XStreamConverter(BooleanConverter::class)
        var voidIndicator: Boolean,

        @XStreamAsAttribute
        @XStreamAlias("EXECUTE_TRADE_DATE")
        @XStreamConverter(LocalDateConverter::class)
        var executeTradeDate: LocalDate,

        @XStreamAsAttribute
        @XStreamAlias("EXECUTE_DATETIME")
        @XStreamConverter(LocalDateTimeConverter::class)
        var executeDateTime: LocalDateTime,

        @XStreamAsAttribute
        @XStreamAlias("SOURCE")
        var source: String?

) {
    constructor() : this(
            executedOrderOid = "", orderOid = "", price = BigDecimal.ZERO, quantity = BigDecimal.ZERO,
            sellerBroker = null, buyerBroker = null, splitIndicator = false, voidIndicator = false,
            executeDateTime = DateTimeUtil.getCurrentDateTime(), executeTradeDate = DateTimeUtil.getCurrentDate(),
            source = null
    )
}