package com.exiasoft.itsstaticdata.model

import com.exiasoft.itscommon.model.enumaration.Status
import java.time.LocalDateTime


interface Instrument {

    var instrumentOid: String

    var instrumentCode: String

    var exchangeBoardOid: String

    var exchangeBoardPriceSpreadOid: String

    var lotSize: Int

    var priceDecimal: Int

    var priceUnit: Int

    var nameDefLang: String

    var name2ndLang: String?

    var name3rdLang: String?

    var shortNameDefLang: String

    var shortName2ndLang: String?

    var shortName3rdLang: String?

    var tradingCurrencyOid: String

    var suspend: Boolean

    var lastModUser: String

    var lastModTimestamp: LocalDateTime

    var syncstr: String

    var status: Status

    var version: Int

    var deleteTime: LocalDateTime?

    var identifier: Id

    data class Id (
            var exchangeBoardOid: String,
            var instrumentCode: String
    ) {
        constructor() : this ("", "")
    }
}