package com.exiasoft.itsaccount.model

import com.exiasoft.itsaccount.converter.TradingAccountStatusConverter
import com.exiasoft.itsaccount.model.enumeration.TradingAccountStatus
import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamConverter

@XStreamAlias("CLIENT_ACCOUNT_2")
data class SimpleTradingAccount(

        @XStreamAsAttribute
        @XStreamAlias("CACC_OID")
        var tradingAccountOid: String,

        @XStreamAsAttribute
        @XStreamAlias("CLIENT_OID")
        var clientOid: String,

        @XStreamAsAttribute
        @XStreamAlias("CACC_CODE")
        var tradingAccountCode: String,

        @XStreamAsAttribute
        @XStreamAlias("OPER_UNIT_OID")
        var operationUnitOid: String,

        @XStreamAsAttribute
        @XStreamAlias("NAME1_DEF_LANG")
        var nameOneDefLang: String,

        @XStreamAsAttribute
        @XStreamAlias("NAME2_DEF_LANG")
        var nameTwoDefLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("NAME1_2ND_LANG")
        var nameOne2ndLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("NAME2_2ND_LANG")
        var nameTwo2ndLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("NAME1_3RD_LANG")
        var nameOne3rdLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("NAME2_3RD_LANG")
        var nameTwo3rdLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("SHORT_NAME_DEF_LANG")
        var shortNameDefLang: String,

        @XStreamAsAttribute
        @XStreamAlias("SHORT_NAME_2ND_LANG")
        var shortName2ndLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("SHORT_NAME_3RD_LANG")
        var shortName3rdLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("CACC_TYPE_OID")
        var tradingAccountTypeOid: String,

        @XStreamAsAttribute
        @XStreamAlias("STATUS") @XStreamConverter(TradingAccountStatusConverter::class)
        var tradingAccountStatus: TradingAccountStatus

) {
    constructor() : this(
            tradingAccountOid = "", clientOid = "", tradingAccountCode = "", operationUnitOid = "",
            nameOneDefLang = "", nameOne2ndLang = null, nameOne3rdLang = null,
            nameTwoDefLang = "", nameTwo2ndLang = null, nameTwo3rdLang = null,
            shortNameDefLang = "", shortName2ndLang = null, shortName3rdLang = null,
            tradingAccountTypeOid = "", tradingAccountStatus = TradingAccountStatus.Active)
}