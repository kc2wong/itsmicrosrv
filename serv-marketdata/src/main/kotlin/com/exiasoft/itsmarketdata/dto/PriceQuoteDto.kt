package com.exiasoft.itsmarketdata.dto

import com.exiasoft.itscommon.serializer.LocalDateTimeDeserializer
import com.exiasoft.itscommon.serializer.LocalDateTimeSerializer
import com.exiasoft.itscommon.util.DateTimeUtil
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.math.BigDecimal
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class PriceQuoteDto(

        var exchangeCode: String,

        var instrumentCode: String,

        var currencyCode: String,

        var shortNameDefLang: String,

        var shortName2ndLang: String?,

        var shortName3rdLang: String?,

        var nominalPrice: BigDecimal?,

        var closingPrice: BigDecimal?,

        var priceChange: BigDecimal?,

        var percentChange: BigDecimal?,

        var bidPrice: BigDecimal?,

        var askPrice: BigDecimal?,

        var dayHigh: BigDecimal?,
        var dayLow: BigDecimal?,
        var fiftyTwoWeekHigh: BigDecimal?,
        var fiftyTwoWeekLow: BigDecimal?,
        var turnover: BigDecimal?,

        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        var quoteDateTime: LocalDateTime

) {
    constructor() : this(
            exchangeCode = "", instrumentCode = "", currencyCode = "", shortNameDefLang = "", shortName2ndLang = null, shortName3rdLang = null,
            nominalPrice = null, closingPrice = null, priceChange = null, percentChange = null, bidPrice = null, askPrice = null,
            dayHigh = null, dayLow = null, fiftyTwoWeekHigh = null, fiftyTwoWeekLow = null, turnover = null, quoteDateTime = DateTimeUtil.getCurrentDateTime())
}