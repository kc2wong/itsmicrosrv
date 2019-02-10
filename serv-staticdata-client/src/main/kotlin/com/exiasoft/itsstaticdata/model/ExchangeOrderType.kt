package com.exiasoft.itsstaticdata.model

import com.exiasoft.itscommon.converter.LocalDateTimeConverter
import com.exiasoft.itscommon.converter.StatusConverter
import com.exiasoft.itscommon.model.enumaration.Status
import com.exiasoft.itscommon.serializer.LocalDateTimeDeserializer
import com.exiasoft.itscommon.serializer.LocalDateTimeSerializer
import com.exiasoft.itscommon.serializer.StatusDeserializer
import com.exiasoft.itscommon.serializer.StatusSerializer
import com.exiasoft.itscommon.util.DateTimeUtil
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamConverter
import java.time.LocalDateTime

@XStreamAlias("EXCH_ORDER_TYPE")
data class ExchangeOrderType(

        @XStreamAsAttribute
        @XStreamAlias("EXCH_ORDER_TYPE_OID")
        var exchangeOrderTypeOid: String,

        @XStreamAsAttribute
        @XStreamAlias("EXCH_OID")
        var exchangeOid: String,

        @XStreamAsAttribute
        @XStreamAlias("ORDER_TYPE_OID")
        var orderTypeOid: String,

        @XStreamAsAttribute
        @XStreamAlias("ORDER_CHNL_OID")
        var orderChannelOid: String,

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
    constructor() : this(exchangeOrderTypeOid = "", exchangeOid = "", orderTypeOid = "", orderChannelOid = "",
            lastModUser = "", lastModTimestamp = DateTimeUtil.getCurrentDateTime(), syncstr = "", status = Status.Active, version = 0)
}