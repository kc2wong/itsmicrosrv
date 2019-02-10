package com.exiasoft.itsstaticdata.model

import com.exiasoft.itscommon.converter.LocalDateTimeConverter
import com.exiasoft.itscommon.converter.StatusConverter
import com.exiasoft.itscommon.model.enumaration.Status
import com.exiasoft.itscommon.serializer.LocalDateTimeDeserializer
import com.exiasoft.itscommon.serializer.LocalDateTimeSerializer
import com.exiasoft.itscommon.serializer.StatusDeserializer
import com.exiasoft.itscommon.serializer.StatusSerializer
import com.exiasoft.itscommon.util.DateTimeUtil
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamConverter
import com.thoughtworks.xstream.annotations.XStreamImplicit
import java.time.LocalDateTime

@XStreamAlias("EXCH_BRD_PRICE_SPREAD")
data class ExchangeBoardPriceSpread(

        @XStreamAsAttribute
        @XStreamAlias("EXCH_BRD_PS_OID")
        var exchangeBoardPriceSpreadOid: String,

        @XStreamAsAttribute
        @XStreamAlias("EXCH_BRD_OID")
        var exchangeBoardOid: String,

        @XStreamAsAttribute
        @XStreamAlias("EXCH_BRD_PS_CODE")
        var exchangeBoardPriceSpreadCode: String,

        @XStreamImplicit
        var exchangeBoardPriceSpreadDetail: List<ExchangeBoardPriceSpreadDetail>,

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
    var identifier: ExchangeBoardPriceSpread.Id
        get() = ExchangeBoardPriceSpread.Id(exchangeBoardOid, exchangeBoardPriceSpreadCode)
        set(value) {
            exchangeBoardOid = value.exchangeBoardOid
            exchangeBoardPriceSpreadCode = value.exchangeBoardPriceSpreadCode
        }

    constructor() : this(
            exchangeBoardPriceSpreadOid = "", exchangeBoardOid = "", exchangeBoardPriceSpreadCode = "", exchangeBoardPriceSpreadDetail = emptyList(),
            lastModUser = "", lastModTimestamp = DateTimeUtil.getCurrentDateTime(), syncstr = "", status = Status.Active, version = 0)

    data class Id(var exchangeBoardOid: String, var exchangeBoardPriceSpreadCode: String) {
    }
}


