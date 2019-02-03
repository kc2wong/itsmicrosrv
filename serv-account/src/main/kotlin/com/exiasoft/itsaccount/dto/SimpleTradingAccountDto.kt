package com.exiasoft.itsaccount.dto

import com.exiasoft.itsaccount.model.enumeration.TradingAccountStatus
import com.exiasoft.itsaccount.serializer.TradingAccountStatusDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

data class SimpleTradingAccountDto (

        var tradingAccountCode: String,

        var operationUnitCode: String,

        var nameOneDefLang: String,

        var nameOne2ndLang: String?,

        var nameOne3rdLang: String?,

        var nameTwoDefLang: String?,

        var nameTwo2ndLang: String?,

        var nameTwo3rdLang: String?,

        var shortNameDefLang: String,

        var shortName2ndLang: String?,

        var shortName3rdLang: String?,

        var tradingAccountTypeCode: String,

        @JsonDeserialize(using = TradingAccountStatusDeserializer::class)
        var tradingAccountStatus: TradingAccountStatus

) {
    constructor() : this(
            tradingAccountCode = "", operationUnitCode = "", nameOneDefLang = "", nameOne2ndLang = null, nameOne3rdLang = null,
            nameTwoDefLang = null, nameTwo2ndLang = null, nameTwo3rdLang = null,
            shortNameDefLang = "", shortName2ndLang = null, shortName3rdLang = null,
            tradingAccountTypeCode = "", tradingAccountStatus = TradingAccountStatus.Active)
}
