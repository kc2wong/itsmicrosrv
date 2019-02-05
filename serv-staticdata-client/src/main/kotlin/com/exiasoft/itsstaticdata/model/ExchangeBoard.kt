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

@XStreamAlias("EXCH_BOARD")
data class ExchangeBoard(

        @XStreamAsAttribute
        @XStreamAlias("EXCH_OID")
        var exchangeOid: String,

        @XStreamAsAttribute
        @XStreamAlias("EXCH_BRD_OID")
        var exchangeBoardOid: String,

        @XStreamAsAttribute
        @XStreamAlias("EXCH_BRD_CODE")
        var exchangeBoardCode: String,

        @XStreamAsAttribute
        @XStreamAlias("NAME1_DEF_LANG")
        var nameDefLang: String,

        @XStreamAsAttribute
        @XStreamAlias("NAME1_2ND_LANG")
        var name2ndLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("NAME1_3RD_LANG")
        var name3rdLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("SHORT_NAME_DEF_LANG")
        var shortNameDefLang: String,

        @XStreamAsAttribute
        @XStreamAlias("SHORT_NAME_2ND_LANG")
        var shortName2ndLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("SHORT_NAME_3RD_LANG")
        var shortName3rdLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("PROHIBIT_BUY_IND")
        @XStreamConverter(BooleanConverter::class)
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var prohibitBuy: Boolean,

        @XStreamAsAttribute @XStreamAlias("SELL_UNSETTLED_STOCK_IND")
        @XStreamConverter(BooleanConverter::class)
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var sellUnsettled: Boolean,

        @XStreamAsAttribute @XStreamAlias("LAST_MOD_USER")
        var lastModUser: String,

        @XStreamAsAttribute
        @XStreamAlias("LAST_MOD_TIMESTAMP")
        @XStreamConverter(LocalDateTimeConverter::class)
        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        var lastModTimestamp: LocalDateTime,

        @XStreamAsAttribute @XStreamAlias("SYNCSTR")
        var syncstr: String,

        @XStreamAsAttribute
        @XStreamAlias("STATUS")
        @XStreamConverter(StatusConverter::class)
        @JsonSerialize(using = StatusSerializer::class)
        @JsonDeserialize(using = StatusDeserializer::class)
        var status: Status,

        @XStreamAsAttribute @XStreamAlias("VERSION")
        var version: Int

) {

    var identifier: ExchangeBoard.Id
        get() = ExchangeBoard.Id(exchangeOid, exchangeBoardCode)
        set(value) {
            exchangeOid = value.exchangeOid
            exchangeBoardCode = value.exchangeBoardCode
        }

    constructor() : this(
            exchangeOid = "", exchangeBoardOid = "", exchangeBoardCode = "",
            nameDefLang = "", name2ndLang = null, name3rdLang = null,
            shortNameDefLang = "", shortName2ndLang = null, shortName3rdLang = null,
            prohibitBuy = true, sellUnsettled = true,
            lastModUser = "", lastModTimestamp = DateTimeUtil.getCurrentDateTime(), syncstr = "", status = Status.Active, version = 0)

    data class Id(
            var exchangeOid: String,
            var exchangeBoardCode: String
    ) {
        constructor() : this("", "")
    }

}

