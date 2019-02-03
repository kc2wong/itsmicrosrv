package com.exiasoft.itsstaticdata.model

import com.exiasoft.itscommon.converter.LocalDateTimeConverter
import com.exiasoft.itscommon.converter.StatusConverter
import com.exiasoft.itscommon.model.enumaration.Status
import com.exiasoft.itscommon.serializer.*
import com.exiasoft.itscommon.util.DateTimeUtil
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamConverter
import java.math.BigDecimal
import java.time.LocalDateTime

@XStreamAlias("EXCH_BRD_PRICE_SPREAD_DTL")
data class ExchangeBoardPriceSpreadDetail(

        @XStreamAsAttribute
        @XStreamAlias("EXCH_BRD_PS_DTL_OID")
        var exchangeBoardPriceSpreadDetailOid: String,

        @XStreamAsAttribute
        @XStreamAlias("EXCH_BRD_PS_OID")
        var exchangeBoardPriceSpreadOid: String,

        @XStreamAsAttribute
        @XStreamAlias("PRICE_FROM")
        @JsonSerialize(using = BigDecimalSerializer::class)
        @JsonDeserialize(using = BigDecimalDeserializer::class)
        var priceFrom: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("PRICE_TO")
        @JsonSerialize(using = BigDecimalSerializer::class)
        @JsonDeserialize(using = BigDecimalDeserializer::class)
        var priceTo: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("SPREAD_VALUE")
        @JsonSerialize(using = BigDecimalSerializer::class)
        @JsonDeserialize(using = BigDecimalDeserializer::class)
        var spreadValue: BigDecimal,

        @XStreamAsAttribute
        @XStreamAlias("LAST_MOD_USER")
        var lastModUser: String,

        @XStreamAsAttribute
        @XStreamAlias("LAST_MOD_TIMESTAMP")
        @XStreamConverter(LocalDateTimeConverter::class)
        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        var lastModTimestamp: LocalDateTime,

        @XStreamAsAttribute
        @XStreamAlias("SYNCSTR")
        var syncstr: String,

        @XStreamAsAttribute
        @XStreamAlias("STATUS")
        @XStreamConverter(StatusConverter::class)
        @JsonSerialize(using = StatusSerializer::class)
        @JsonDeserialize(using = StatusDeserializer::class)
        var status: Status,

        @XStreamAsAttribute
        @XStreamAlias("VERSION")
        var version: Int
) {
    constructor() : this(
            exchangeBoardPriceSpreadDetailOid = "", exchangeBoardPriceSpreadOid = "",
            priceFrom = BigDecimal.ZERO, priceTo = BigDecimal.ZERO, spreadValue = BigDecimal.ZERO,
            lastModUser = "", lastModTimestamp = DateTimeUtil.getCurrentDateTime(), syncstr = "", status = Status.Active, version = 0)

    data class Id(var exchangeBoardPriceSpreadOid: String, var priceFrom: BigDecimal) {
    }

}


