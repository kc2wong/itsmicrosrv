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

@XStreamAlias("ORDER_TYPE")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class OrderType(

        @XStreamAsAttribute
        @XStreamAlias("ORDER_TYPE_OID")
        var orderTypeOid: String,

        @XStreamAsAttribute
        @XStreamAlias("ORDER_TYPE_CODE")
        var orderTypeCode: String,

        @XStreamAsAttribute
        @XStreamAlias("DESCPT_DEF_LANG")
        var descptDefLang: String,

        @XStreamAsAttribute
        @XStreamAlias("DESCPT_2ND_LANG")
        var descpt2ndLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("DESCPT_3RD_LANG")
        var descpt3rdLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("SEQUENCE")
        var sequence: Int,

        @XStreamAsAttribute
        @XStreamAlias("PRICE_ALLOWED_IND")
        @XStreamConverter(BooleanConverter::class)
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var priceAllowedIndicator: Boolean,

        @XStreamAsAttribute
        @XStreamAlias("PRICE_REQUIRED_FOR_EA_IND")
        @XStreamConverter(BooleanConverter::class)
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var priceRequiredForEaIndicator: Boolean,

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
            orderTypeOid = "", orderTypeCode = "", descptDefLang = "", descpt2ndLang = null, descpt3rdLang = null,
            sequence = 0, priceAllowedIndicator = false, priceRequiredForEaIndicator = false,
            lastModUser = "", lastModTimestamp = DateTimeUtil.getCurrentDateTime(), syncstr = "", status = Status.Active, version = 0)
}