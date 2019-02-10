package com.exiasoft.itsstaticdata.model

import com.exiasoft.itscommon.model.enumaration.Status
import java.time.LocalDate
import java.time.LocalDateTime

interface SimpleExchange {

    var exchangeOid: String

    var exchangeCode: String

    var sequence: Int

    var baseCurrencyOid: String

    var nameDefLang: String

    var name2ndLang: String?

    var name3rdLang: String?

    var shortNameDefLang: String

    var shortName2ndLang: String?

    var shortName3rdLang: String?

    var tradeDate: LocalDate

    var prevTradeDate: LocalDate

    var lastModUser: String

    var lastModTimestamp: LocalDateTime

    var syncstr: String

    var status: Status

    var version: Int

}