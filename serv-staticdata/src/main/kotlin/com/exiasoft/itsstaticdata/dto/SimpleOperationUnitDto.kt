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
data class SimpleOperationUnitDto (

        var operationUnitCode: String,

        var nameOneDefLang: String,

        var nameTwoDefLang: String?,

        var nameOne2ndLang: String?,

        var nameTwo2ndLang: String?,

        var nameOne3rdLang: String?,

        var nameTwo3rdLang: String?,

        var shortNameDefLang: String,

        var shortName2ndLang: String?,

        var shortName3rdLang: String?,

        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var demoIndicator: Boolean,

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
            operationUnitCode = "",
            nameOneDefLang = "", nameOne2ndLang = null, nameOne3rdLang = null,
            nameTwoDefLang = "", nameTwo2ndLang = null, nameTwo3rdLang = null,
            shortNameDefLang = "", shortName2ndLang = null, shortName3rdLang = null, demoIndicator = false,
            lastModUser = "", lastModTimestamp = DateTimeUtil.getCurrentDateTime(), syncstr = "", status = Status.Active, version = 0)
}