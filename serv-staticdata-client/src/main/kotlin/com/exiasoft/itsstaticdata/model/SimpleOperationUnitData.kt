package com.exiasoft.itsstaticdata.model

import com.exiasoft.itscommon.converter.BooleanConverter
import com.exiasoft.itscommon.converter.LocalDateTimeConverter
import com.exiasoft.itscommon.converter.StatusConverter
import com.exiasoft.itscommon.model.enumaration.Status
import com.exiasoft.itscommon.serializer.*
import com.exiasoft.itscommon.util.DateTimeUtil
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamConverter
import java.time.LocalDateTime

@XStreamAlias("OPERATION_UNIT")
data class SimpleOperationUnitData (

        @XStreamAsAttribute
        @XStreamAlias("OPER_UNIT_OID")
        override var operationUnitOid: String,

        @XStreamAsAttribute
        @XStreamAlias("OPER_UNIT_CODE")
        override var operationUnitCode: String,

        @XStreamAsAttribute
        @XStreamAlias("NAME1_DEF_LANG")
        override var nameOneDefLang: String,

        @XStreamAsAttribute
        @XStreamAlias("NAME2_DEF_LANG")
        override var nameTwoDefLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("NAME1_2ND_LANG")
        override var nameOne2ndLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("NAME2_2ND_LANG")
        override var nameTwo2ndLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("NAME1_3RD_LANG")
        override var nameOne3rdLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("NAME2_3RD_LANG")
        override var nameTwo3rdLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("SHORT_NAME_DEF_LANG")
        override var shortNameDefLang: String,

        @XStreamAsAttribute
        @XStreamAlias("SHORT_NAME_2ND_LANG")
        override var shortName2ndLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("SHORT_NAME_3RD_LANG")
        override var shortName3rdLang: String?,

        @XStreamAsAttribute
        @XStreamAlias("DEMO_OU_IND")
        @XStreamConverter(BooleanConverter::class)
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        override var demoIndicator: Boolean,

        @XStreamAsAttribute
        @XStreamAlias("LAST_MOD_USER")
        override var lastModUser: String,

        @XStreamAsAttribute
        @XStreamAlias("LAST_MOD_TIMESTAMP")
        @XStreamConverter(LocalDateTimeConverter::class)
        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        override var lastModTimestamp: LocalDateTime,

        @XStreamAsAttribute
        @XStreamAlias("SYNCSTR")
        override var syncstr: String,

        @XStreamAsAttribute
        @XStreamAlias("STATUS")
        @XStreamConverter(StatusConverter::class)
        @JsonDeserialize(using = StatusDeserializer::class)
        override var status: Status,

        @XStreamAsAttribute
        @XStreamAlias("VERSION")
        override var version: Int
) : SimpleOperationUnit {
    constructor() : this(
            operationUnitOid = "", operationUnitCode = "",
            nameOneDefLang = "", nameOne2ndLang = null, nameOne3rdLang = null,
            nameTwoDefLang = "", nameTwo2ndLang = null, nameTwo3rdLang = null,
            shortNameDefLang = "", shortName2ndLang = null, shortName3rdLang = null, demoIndicator = false,
            lastModUser = "", lastModTimestamp = DateTimeUtil.getCurrentDateTime(), syncstr = "", status = Status.Active, version = 0)
}