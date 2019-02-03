package com.exiasoft.itsstaticdata.dto

import capital.scalable.restdocs.jackson.RestdocsNotExpanded
import com.exiasoft.itscommon.model.enumaration.Status
import com.exiasoft.itscommon.serializer.*
import com.exiasoft.itscommon.util.DateTimeUtil
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.sun.istack.NotNull
import java.time.LocalDate
import java.time.LocalDateTime
import javax.validation.constraints.Size

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class SimpleExchangeDto(

        /**
         * Exchange Code
         */
        @NotNull
        @Size(max = 10)
        var exchangeCode: String,

        /**
         * Sequence for display value in dropdown list.  Exchange with smallest sequence will also be used as default Exchange
         */
        @NotNull
        var sequence: Int,

        /**
         * Base currency code
         */
        @NotNull
        @Size(max = 5)
        var baseCurrencyCode: String,

        /**
         * Name in English
         */
        @NotNull
        @Size(max = 35)
        var nameDefLang: String,

        /**
         * Name in Traditional Chinese
         */
        @Size(max = 35)
        var name2ndLang: String?,

        /**
         * Name in Simplified Chinese
         */
        @Size(max = 35)
        var name3rdLang: String?,

        /**
         * Shortname in English
         */
        @NotNull
        @Size(max = 15)
        var shortNameDefLang: String,

        /**
         * Shortname in Traditional Chinese
         */
        @Size(max = 15)
        var shortName2ndLang: String?,

        /**
         * Shortname in Simplified Chinese
         */
        @Size(max = 15)
        var shortName3rdLang: String?,

        /**
         * Current trade date
         */
        @NotNull
        @JsonSerialize(using = LocalDateSerializer::class)
        @JsonDeserialize(using = LocalDateDeserializer::class)
        @RestdocsNotExpanded
        var tradeDate: LocalDate,

        /**
         * Previous trade date
         */
        @NotNull
        @JsonSerialize(using = LocalDateSerializer::class)
        @JsonDeserialize(using = LocalDateDeserializer::class)
        @RestdocsNotExpanded
        var prevTradeDate: LocalDate,

        /**
         * Last modified user
         */
        @NotNull
        @Size(max = 20)
        var lastModUser: String,

        /**
         * Last modified datetime
         */
        @NotNull
        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        @RestdocsNotExpanded
        var lastModTimestamp: LocalDateTime,

        /**
         * Syncstr
         */
        @NotNull
        @Size(max = 20)
        var syncstr: String,

        /**
         * Status
         */
        @NotNull
        @JsonDeserialize(using = StatusDeserializer::class)
        var status: Status,

        /**
         * Version
         */
        @NotNull
        var version: Int
) {
    constructor() : this(
            exchangeCode = "", sequence = 0, baseCurrencyCode = "",
            nameDefLang = "", name2ndLang = null, name3rdLang = null,
            shortNameDefLang = "", shortName2ndLang = null, shortName3rdLang = null,
            tradeDate = DateTimeUtil.getCurrentDate(), prevTradeDate = DateTimeUtil.getCurrentDate(),
//            exchangeParameter = emptyList(), exchangeOrderType = emptyList(),
            lastModUser = "", lastModTimestamp = DateTimeUtil.getCurrentDateTime(), syncstr = "", status = Status.Active, version = 0)
}