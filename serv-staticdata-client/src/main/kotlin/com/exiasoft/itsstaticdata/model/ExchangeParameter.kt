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

@XStreamAlias("EXCH_PARA")
data class ExchangeParameter(

        @XStreamAsAttribute
        @XStreamAlias("EXCH_PARA_OID")
        var exchangeParaOid: String,

        @XStreamAsAttribute
        @XStreamAlias("EXCH_OID")
        var exchangeOid: String,

        @XStreamAsAttribute
        @XStreamAlias("PARAMETER_CODE")
        var parameterCode: String,

        @XStreamAsAttribute
        @XStreamAlias("DESCPT")
        var description: String,

        @XStreamAsAttribute
        @XStreamAlias("PARAMETER_VALUE")
        var parameterValue: String,

        @XStreamAsAttribute
        @XStreamAlias("LOOKUP_TYPE_CODE")
        var lookupTypeCode: String?,

        @XStreamAsAttribute
        @XStreamAlias("SEQUENCE")
        var sequence: Int,

        @XStreamAsAttribute
        @XStreamAlias("SYSTEM_IND")
        @XStreamConverter(BooleanConverter::class)
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var systemIndicator: Boolean,

        @XStreamAsAttribute
        @XStreamAlias("LAST_MOD_USER")
        var lastModUser: String,

        @XStreamAsAttribute
        @XStreamAlias("LAST_MOD_TIMESTAMP")
        @XStreamConverter(LocalDateTimeConverter::class)
        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        var lastModTimestamp: LocalDateTime,

        @XStreamAsAttribute
        @XStreamAlias("SYNCSTR")
        var syncstr: String,

        @XStreamAsAttribute
        @XStreamAlias("STATUS")
        @XStreamConverter(StatusConverter::class)
        @JsonSerialize(using = StatusSerializer::class)
        @JsonDeserialize(using = StatusDeserializer::class)
        var status: Status,

        @XStreamAsAttribute
        @XStreamAlias("VERSION")
        var version: Int
) {
    constructor() : this(
            exchangeParaOid = "", exchangeOid = "", parameterCode = "", parameterValue = "",
            description = "", lookupTypeCode = null, sequence = 0, systemIndicator = false,
            lastModUser = "", lastModTimestamp = DateTimeUtil.getCurrentDateTime(), syncstr = "", status = Status.Active, version = 0)
}