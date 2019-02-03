package com.exiasoft.itsstaticdata.dto

import com.exiasoft.itscommon.model.enumaration.Status
import com.exiasoft.itscommon.serializer.*
import com.exiasoft.itscommon.util.DateTimeUtil
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class InstrumentDto(

        var instrumentCode: String,

        var exchangeCode: String,

        var exchangeBoardCode: String,

        var exchangeBoardPsCode: String,

        var lotSize: Int,

        var priceDecimal: Int,

        var priceUnit: Int,

        var nameDefLang: String,

        var name2ndLang: String?,

        var name3rdLang: String?,

        var shortNameDefLang: String,

        var shortName2ndLang: String?,

        var shortName3rdLang: String?,

        var tradingCurrencyCode: String,

        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var suspend: Boolean,

        var lastModUser: String,

        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        var lastModTimestamp: LocalDateTime,

        var syncstr: String,

        @JsonSerialize(using = StatusSerializer::class)
        @JsonDeserialize(using = StatusDeserializer::class)
        var status: Status,

        var version: Int,

        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        var deleteTime: LocalDateTime?

) {
    constructor() : this(
            instrumentCode = "", exchangeCode = "", exchangeBoardCode = "", exchangeBoardPsCode = "",
            lotSize = 0, priceDecimal = 0, priceUnit = 0,
            nameDefLang = "", name2ndLang = null, name3rdLang = null, shortNameDefLang = "", shortName2ndLang = null, shortName3rdLang = null,
            tradingCurrencyCode = "", suspend = false,
            lastModUser = "", lastModTimestamp = DateTimeUtil.getCurrentDateTime(), syncstr = "", status = Status.Active, version = 0, deleteTime = null)
}