package com.exiasoft.itsstaticdata.dto

import capital.scalable.restdocs.jackson.RestdocsNotExpanded
import com.exiasoft.itscommon.model.enumaration.Status
import com.exiasoft.itscommon.serializer.LocalDateTimeDeserializer
import com.exiasoft.itscommon.serializer.LocalDateTimeSerializer
import com.exiasoft.itscommon.serializer.StatusDeserializer
import com.exiasoft.itscommon.util.DateTimeUtil
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.time.LocalDateTime
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class CurrencyDto(
        /**
         * Currency Code
         */
        @NotNull
        @Size(min = 1, max = 10)
        var currencyCode: String,

        /**
         * ISO Code
         */
        @Size(min = 2, max = 3)
        var isoCode: String?,

        /**
         * Decimal Point
         */
        @NotNull
        var decimalPoint: Int,

        /**
         * Description in English
         */
        @NotNull
        @Size(max = 35)
        var descptDefLang: String,

        /**
         * Description in Traditional Chinese
         */
        @Size(max = 35)
        var descpt2ndLang: String?,

        /**
         * Description in Simplified Chinese
         */
        @Size(max = 35)
        var descpt3rdLang: String?,

        /**
         * Unit name
         */
        @Size(max = 10)
        var unitName: String?,

        /**
         * Subunit name
         */
        @Size(max = 10)
        var subUnitName: String?,

        /**
         * Last modified user
         */
        @NotNull
        @Size(max = 20)
        var lastModUser: String,

        /**
         * Last modified datetime.  LocalDateTime in YYYY-MM-DD HH:MI:SS format
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
            currencyCode = "", isoCode = null, decimalPoint = 0,
            descptDefLang = "", descpt2ndLang = null, descpt3rdLang = null,
            unitName = null, subUnitName = null,
            lastModUser = "", lastModTimestamp = DateTimeUtil.getCurrentDateTime(), syncstr = "", status = Status.Active, version = 0)
}