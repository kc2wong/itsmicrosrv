package com.exiasoft.itsorder.model

import com.exiasoft.itscommon.converter.LocalDateConverter
import com.exiasoft.itscommon.converter.LocalDateTimeConverter
import com.exiasoft.itscommon.util.DateTimeUtil
import com.exiasoft.itsorder.converter.BuySellConverter
import com.exiasoft.itsorder.converter.OrderConverter
import com.exiasoft.itsorder.converter.OrderStatusConverter
import com.exiasoft.itsorder.model.enumeration.BuySell
import com.exiasoft.itsorder.model.enumeration.OrderStatus
import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamConverter
import com.thoughtworks.xstream.annotations.XStreamImplicit
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@XStreamAlias("ORDER")
@XStreamConverter(OrderConverter::class)
data class Order(

        @XStreamAsAttribute
        @XStreamAlias("ORDER_OID")
        override var orderOid: String,

        @XStreamAsAttribute
        @XStreamAlias("ORDER_NO")
        override var orderNumber: String,

        @XStreamAsAttribute
        @XStreamAlias("SIDE")
        @XStreamConverter(BuySellConverter::class)
        override var buySell: BuySell,

        @XStreamAsAttribute
        @XStreamAlias("CACC_CODE")
        override var tradingAccountCode: String,

        @XStreamAsAttribute
        @XStreamAlias("EXCHANGE_CODE")
        override var exchangeCode: String,

        @XStreamAsAttribute
        @XStreamAlias("MARKET_INSTRUMENT_OID")
        override var instrumentOid: String,

        @XStreamAsAttribute
        @XStreamAlias("MARKET_INSTRUMENT_NO")
        override var instrumentCode: String,

        @XStreamAsAttribute
        @XStreamAlias("PRICE")
        override var price: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("QUANTITY")
        override var quantity: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("CHARGE_AMT")
        override var chargeAmount: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("COMM_AMT")
        override var commissionAmount: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("GROSS_CONSIDERATION")
        override var grossAmount: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("NET_AMOUNT")
        override var netAmount: BigDecimal,

        // NET_AMOUNT is same as NET_CON, If NET_CON but not NET_AMOUNT is provided in XML, the copy NET_CON to NET_AMOUNT
        // in SimpleOrderConverter
        @XStreamAsAttribute
        @XStreamAlias("NET_CON")
        var netConsideration: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("EXECUTED_QUANTITY")
        override var executedQuantity: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("EXECUTED_AMT")
        override var executedAmount: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("ORDER_STATUS")
        @XStreamConverter(OrderStatusConverter::class)
        override var orderStatus: OrderStatus,

        @XStreamAsAttribute
        @XStreamAlias("CREATE_DATETIME")
        @XStreamConverter(LocalDateTimeConverter::class)
        override var createDateTime: LocalDateTime,

        @XStreamAsAttribute
        @XStreamAlias("CREATE_TRADE_DATE")
        @XStreamConverter(LocalDateConverter::class)
        override var createTradeDate: LocalDate,

        @XStreamAsAttribute
        @XStreamAlias("LAST_UPD_DATETIME")
        @XStreamConverter(LocalDateTimeConverter::class)
        override var updateDateTime: LocalDateTime,

        @XStreamAsAttribute
        @XStreamAlias("LAST_UPD_TRADE_DATE")
        @XStreamConverter(LocalDateConverter::class)
        override var updateTradeDate: LocalDate,

        @XStreamAsAttribute
        @XStreamAlias("REJECT_REASON")
        override var rejectReason: String?,

        @XStreamAsAttribute
        @XStreamAlias("INPUT_SOURCE")
        override var orderChannelCode: String?,

        @XStreamImplicit
        var orderExecution: List<OrderExecution>

) : SimpleOrder {
    constructor() : this(
            orderOid = "", orderNumber = "", buySell = BuySell.Buy, tradingAccountCode = "", exchangeCode = "",
            instrumentOid = "", instrumentCode = "", price = BigDecimal.ZERO, quantity = BigDecimal.ZERO,
            chargeAmount = BigDecimal.ZERO, commissionAmount = BigDecimal.ZERO,
            grossAmount = BigDecimal.ZERO, netAmount = BigDecimal.ZERO, netConsideration = BigDecimal.ZERO,
            executedQuantity = BigDecimal.ZERO, executedAmount = BigDecimal.ZERO, orderStatus = OrderStatus.Pending,
            createDateTime = DateTimeUtil.getCurrentDateTime(), createTradeDate = DateTimeUtil.getCurrentDate(),
            updateDateTime = DateTimeUtil.getCurrentDateTime(), updateTradeDate = DateTimeUtil.getCurrentDate(),
            rejectReason = null, orderChannelCode = null, orderExecution = emptyList()
    )
}