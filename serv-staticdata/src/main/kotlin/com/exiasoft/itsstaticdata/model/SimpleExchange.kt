package com.exiasoft.itsstaticdata.model

import com.exiasoft.itscommon.converter.LocalDateConverter
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
import java.time.LocalDate
import java.time.LocalDateTime


@XStreamAlias("EXCHANGE")
data class SimpleExchange(

        @XStreamAsAttribute
        @XStreamAlias("EXCH_OID")
        var exchangeOid: String,

        @XStreamAsAttribute
        @XStreamAlias("EXCH_CODE")
        var exchangeCode: String,

        @XStreamAsAttribute
        @XStreamAlias("SEQUENCE")
        var sequence: Int,

        @XStreamAsAttribute
        @XStreamAlias("BASE_CCY_OID")
        var baseCurrencyOid: String,

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
        @XStreamAlias("TRADE_DATE")
        @XStreamConverter(LocalDateConverter::class)
        @JsonSerialize(using = LocalDateSerializer::class)
        @JsonDeserialize(using = LocalDateDeserializer::class)
        var tradeDate: LocalDate,

        @XStreamAsAttribute
        @XStreamAlias("PREV_TRADE_DATE")
        @XStreamConverter(LocalDateConverter::class)
        @JsonSerialize(using = LocalDateSerializer::class)
        @JsonDeserialize(using = LocalDateDeserializer::class)
        var prevTradeDate: LocalDate,

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
            exchangeOid = "", exchangeCode = "", sequence = 0, baseCurrencyOid = "",
            nameDefLang = "", name2ndLang = null, name3rdLang = null,
            shortNameDefLang = "", shortName2ndLang = null, shortName3rdLang = null,
            tradeDate = DateTimeUtil.getCurrentDate(), prevTradeDate = DateTimeUtil.getCurrentDate(),
            lastModUser = "", lastModTimestamp = DateTimeUtil.getCurrentDateTime(),
            syncstr = "", status = Status.Active, version = 0)
}