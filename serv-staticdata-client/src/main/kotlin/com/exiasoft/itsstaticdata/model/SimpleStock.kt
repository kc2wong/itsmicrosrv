package com.exiasoft.itsstaticdata.model

import com.exiasoft.itscommon.converter.BooleanConverter
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
import java.time.LocalDateTime

@XStreamAlias("STOCK")
data class SimpleStock(

        @XStreamAsAttribute
        @XStreamAlias("INSTRUMENT_OID")
        override var instrumentOid: String,

        @XStreamAsAttribute
        @XStreamAlias("INSTRUMENT_CODE")
        override var instrumentCode: String,

        @XStreamAsAttribute
        @XStreamAlias("EXCH_BRD_OID")
        override var exchangeBoardOid: String,

        @XStreamAsAttribute
        @XStreamAlias("EXCH_BRD_PS_OID")
        override var exchangeBoardPriceSpreadOid: String,

        @XStreamAsAttribute
        @XStreamAlias("LOT_SIZE")
        override var lotSize: Int,

        @XStreamAsAttribute
        @XStreamAlias("PRICE_DECIMAL")
        override var priceDecimal: Int,

        @XStreamAsAttribute
        @XStreamAlias("PRICE_UNIT")
        override var priceUnit: Int,

        @XStreamAsAttribute @XStreamAlias("NAME1_DEF_LANG")
        override var nameDefLang: String,

        @XStreamAsAttribute
        @XStreamAlias("NAME1_2ND_LANG")
        override var name2ndLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("NAME1_3RD_LANG")
        override var name3rdLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("SHORT_NAME_DEF_LANG")
        override var shortNameDefLang: String,

        @XStreamAsAttribute
        @XStreamAlias("SHORT_NAME_2ND_LANG")
        override var shortName2ndLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("SHORT_NAME_3RD_LANG")
        override var shortName3rdLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("TRADING_CCY_OID")
        override var tradingCurrencyOid: String,

        @XStreamAsAttribute
        @XStreamAlias("SUSPEND_IND")
        @XStreamConverter(BooleanConverter::class)
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        override var suspend: Boolean,

        @XStreamAsAttribute
        @XStreamAlias("LAST_MAKER")
        override var lastModUser: String,

        @XStreamAsAttribute
        @XStreamAlias("LAST_MAKE_DATE")
        @XStreamConverter(LocalDateTimeConverter::class)
        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        override var lastModTimestamp: LocalDateTime,

        @XStreamAsAttribute
        @XStreamAlias("SYNCSTR")
        override var syncstr: String,

        @XStreamAsAttribute
        @XStreamAlias("INSTMT_STATUS")
        @XStreamConverter(StatusConverter::class)
        @JsonSerialize(using = StatusSerializer::class)
        @JsonDeserialize(using = StatusDeserializer::class)
        override var status: Status,

        @XStreamAsAttribute @XStreamAlias("VERSION")
        override var version: Int,

        @XStreamAsAttribute
        @XStreamAlias("DELETETIME")
        @XStreamConverter(LocalDateTimeConverter::class)
        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        override var deleteTime: LocalDateTime?

) : Instrument {

        override var identifier: Instrument.Id
                get() = Instrument.Id(exchangeBoardOid, instrumentCode)
                set(value) {
                        exchangeBoardOid = value.exchangeBoardOid
                        instrumentCode = value.instrumentCode
                }

        constructor() : this(
            instrumentOid = "", instrumentCode = "", exchangeBoardOid = "", exchangeBoardPriceSpreadOid = "",
            lotSize = 0, priceDecimal = 0, priceUnit = 0,
            nameDefLang = "", name2ndLang = null, name3rdLang = null, shortNameDefLang = "", shortName2ndLang = null, shortName3rdLang = null,
            tradingCurrencyOid = "", suspend = false,
            lastModUser = "", lastModTimestamp = DateTimeUtil.getCurrentDateTime(), syncstr = "", status = Status.Active, version = 0, deleteTime = null)
}