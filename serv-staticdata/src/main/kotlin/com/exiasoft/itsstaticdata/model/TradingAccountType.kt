package com.exiasoft.itsstaticdata.model

import com.exiasoft.itscommon.converter.LocalDateTimeConverter
import com.exiasoft.itscommon.converter.StatusConverter
import com.exiasoft.itscommon.model.enumaration.Status
import com.exiasoft.itscommon.serializer.LocalDateTimeDeserializer
import com.exiasoft.itscommon.serializer.LocalDateTimeSerializer
import com.exiasoft.itscommon.serializer.StatusDeserializer
import com.exiasoft.itscommon.util.DateTimeUtil
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamConverter
import java.time.LocalDateTime

@XStreamAlias("CACC_TYPE")
data class TradingAccountType(

        @XStreamAsAttribute
        @XStreamAlias("CACC_TYPE_OID")
        var tradingAccountTypeOid: String,

        @XStreamAsAttribute
        @XStreamAlias("CACC_TYPE_CODE")
        var tradingAccountTypeCode: String,

        @XStreamAsAttribute
        @XStreamAlias("DESCPT")
        var description: String,

        @XStreamAsAttribute
        @XStreamAlias("SEQUENCE")
        var sequence: Int,

        @XStreamAsAttribute @XStreamAlias("LAST_MOD_USER")
        var lastModUser: String,

        @XStreamAsAttribute @XStreamAlias("LAST_MOD_TIMESTAMP")
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
        @JsonDeserialize(using = StatusDeserializer::class)
        var status: Status,

        @XStreamAsAttribute
        @XStreamAlias("VERSION")
        var version: Int,

        @XStreamAsAttribute
        @XStreamAlias("DELETETIME")
        @XStreamConverter(LocalDateTimeConverter::class)
        @JsonSerialize(using = LocalDateTimeSerializer::class)
        var deleteTime: LocalDateTime?

) {
    constructor() : this(
            tradingAccountTypeOid = "", tradingAccountTypeCode = "", description = "", sequence = 0,
            lastModUser = "", lastModTimestamp = DateTimeUtil.getCurrentDateTime(), syncstr = "", status = Status.Active, version = 0, deleteTime = null)
}
