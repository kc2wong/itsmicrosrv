package com.exiasoft.itsstaticdata.dto

import com.exiasoft.itscommon.model.enumaration.Status
import com.exiasoft.itscommon.serializer.LocalDateTimeDeserializer
import com.exiasoft.itscommon.serializer.LocalDateTimeSerializer
import com.exiasoft.itscommon.serializer.StatusDeserializer
import com.exiasoft.itscommon.util.DateTimeUtil
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.time.LocalDateTime

data class OrderTypeDto(

        var orderTypeCode: String,

        var descptDefLang: String,

        var descpt2ndLang: String?,

        var descpt3rdLang: String?,

        var sequence: Int,

        var priceAllowedIndicator: Boolean,

        var priceRequiredForEaIndicator: Boolean,

        var lastModUser: String,

        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        var lastModTimestamp: LocalDateTime,

        var syncstr: String,

        @JsonDeserialize(using = StatusDeserializer::class)
        var status: Status,

        var version: Int
) {
    constructor() : this(
            orderTypeCode = "", descptDefLang = "", descpt2ndLang = null, descpt3rdLang = null,
            sequence = 0, priceAllowedIndicator = false, priceRequiredForEaIndicator = false,
            lastModUser = "", lastModTimestamp = DateTimeUtil.getCurrentDateTime(), syncstr = "", status = Status.Active, version = 0)
}