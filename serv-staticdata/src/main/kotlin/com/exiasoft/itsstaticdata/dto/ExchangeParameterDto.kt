package com.exiasoft.itsstaticdata.dto

import com.exiasoft.itscommon.model.enumaration.Status
import com.exiasoft.itscommon.serializer.LocalDateTimeDeserializer
import com.exiasoft.itscommon.serializer.LocalDateTimeSerializer
import com.exiasoft.itscommon.serializer.StatusDeserializer
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
data class ExchangeParameterDto(

        var parameterCode: String,

        var description: String,

        var parameterValue: String,

        var lookupTypeCode: String?,

        var sequence: Int,

        var systemIndicator: Boolean,

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
            parameterCode = "", parameterValue = "", description = "", lookupTypeCode = null, sequence = 0, systemIndicator = false,
            lastModUser = "", lastModTimestamp = DateTimeUtil.getCurrentDateTime(), syncstr = "", status = Status.Active, version = 0)
}