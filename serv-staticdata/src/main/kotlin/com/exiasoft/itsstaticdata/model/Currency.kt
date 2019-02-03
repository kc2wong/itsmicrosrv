package com.exiasoft.itsstaticdata.model

import com.exiasoft.itscommon.converter.BooleanConverter
import com.exiasoft.itscommon.converter.LocalDateTimeConverter
import com.exiasoft.itscommon.converter.StatusConverter
import com.exiasoft.itscommon.model.enumaration.Status
import com.exiasoft.itscommon.serializer.*
import com.exiasoft.itscommon.util.DateTimeUtil
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamConverter
import java.time.LocalDateTime

@XStreamAlias("CURRENCY")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class Currency(

        @XStreamAsAttribute @XStreamAlias("CURRENCY_OID")
        var currencyOid: String,

        @XStreamAsAttribute @XStreamAlias("CURRENCY_CODE")
        var currencyCode: String,

        @XStreamAsAttribute @XStreamAlias("ISO_CODE")
        var isoCode: String?,

        @XStreamAsAttribute @XStreamAlias("DECIMAL_POINT")
        var decimalPoint: Int,

        @XStreamAsAttribute @XStreamAlias("DESCPT_DEF_LANG")
        var descptDefLang: String,

        @XStreamAsAttribute @XStreamAlias("DESCPT_2ND_LANG")
        var descpt2ndLang: String?,

        @XStreamAsAttribute @XStreamAlias("DESCPT_3RD_LANG")
        var descpt3rdLang: String?,

        @XStreamAsAttribute @XStreamAlias("UNIT_NAME")
        var unitName: String?,

        @XStreamAsAttribute @XStreamAlias("SUB_UNIT_NAME")
        var subUnitName: String?,

        @XStreamAsAttribute @XStreamAlias("LAST_MOD_USER")
        var lastModUser: String,

        @XStreamAsAttribute
        @XStreamAlias("LAST_MOD_TIMESTAMP")
        @XStreamConverter(LocalDateTimeConverter::class)
        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        var lastModTimestamp: LocalDateTime,

        @XStreamAsAttribute
        @XStreamAlias("EXCLUDE_FROM_CR_LN_IND")
        @XStreamConverter(BooleanConverter::class)
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var excludeFromCreditLine: Boolean,

        @XStreamAsAttribute
        @XStreamAlias("EXCLUDE_FROM_CURRENCY_POOL_IND")
        @XStreamConverter(BooleanConverter::class)
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var excludeFromCurrencyPool: Boolean?,

        @XStreamAsAttribute
        @XStreamAlias("EXCLUDE_FROM_MARGIN_POOL_IND")
        @XStreamConverter(BooleanConverter::class)
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var excludeFromMarginPool: Boolean,

        @XStreamAsAttribute
        @XStreamAlias("REJ_INSUFF_FUND_ORDER_IND")
        @XStreamConverter(BooleanConverter::class)
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var rejectInsuffFundOrder: Boolean,

        @XStreamAsAttribute
        @XStreamAlias("SYNCSTR")
        var syncstr: String,

        @XStreamAsAttribute
        @XStreamAlias("STATUS")
        @XStreamConverter(StatusConverter::class)
        @JsonSerialize(using = StatusSerializer::class)
        @JsonDeserialize(using = StatusDeserializer::class)
        var status: Status,

        @XStreamAsAttribute @XStreamAlias("VERSION")
        var version: Int,

        @XStreamAsAttribute
        @XStreamAlias("DELETETIME")
        @XStreamConverter(LocalDateTimeConverter::class)
        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        var deleteTime: LocalDateTime?

) {
    constructor() : this(
            currencyOid = "", currencyCode = "", isoCode = null,
            decimalPoint = 0, descptDefLang = "", descpt2ndLang = null, descpt3rdLang = null,
            unitName = null, subUnitName = null,
            excludeFromCreditLine = false, excludeFromCurrencyPool = null, excludeFromMarginPool = false, rejectInsuffFundOrder = false,
            lastModUser = "", lastModTimestamp = DateTimeUtil.getCurrentDateTime(), syncstr = "", status = Status.Active, version = 0, deleteTime = null)
}