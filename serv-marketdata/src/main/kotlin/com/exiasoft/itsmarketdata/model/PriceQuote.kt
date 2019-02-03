package com.exiasoft.itsmarketdata.model

import com.exiasoft.itscommon.util.DateTimeUtil
import java.math.BigDecimal
import java.time.LocalDateTime

data class PriceQuote(

        var exchangeOid: String,

        var instrumentCode: String,

        var currencyOid: String,

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
        var quoteDateTime: LocalDateTime

) {
    constructor() : this(
            exchangeOid = "", instrumentCode = "", currencyOid = "", shortNameDefLang = "", shortName2ndLang = null, shortName3rdLang = null,
            nominalPrice = null, closingPrice = null, priceChange = null, percentChange = null, bidPrice = null, askPrice = null,
            dayHigh = null, dayLow = null, fiftyTwoWeekHigh = null, fiftyTwoWeekLow = null, turnover = null, quoteDateTime = DateTimeUtil.getCurrentDateTime())
}