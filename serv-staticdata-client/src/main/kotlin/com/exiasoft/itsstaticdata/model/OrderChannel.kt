package com.exiasoft.itsstaticdata.model

import com.exiasoft.itscommon.converter.BooleanConverter
import com.exiasoft.itscommon.converter.LocalDateTimeConverter
import com.exiasoft.itscommon.converter.StatusConverter
import com.exiasoft.itscommon.model.enumaration.Status
import com.exiasoft.itscommon.util.DateTimeUtil
import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamConverter
import java.time.LocalDateTime

@XStreamAlias("ORDER_CHNL")
data class OrderChannel(

        @XStreamAsAttribute
        @XStreamAlias("ORDER_CHNL_OID")
        var orderChannelOid: String,

        @XStreamAsAttribute
        @XStreamAlias("ORDER_CHNL_CODE")
        var orderChannelCode: String,

        @XStreamAsAttribute
        @XStreamAlias("DESCPT")
        var description: String,

        @XStreamAsAttribute
        @XStreamAlias("SEQUENCE")
        var sequence: Int,

        @XStreamAsAttribute
        @XStreamAlias("SYSTEM_IND")
        @XStreamConverter(BooleanConverter::class)
        var systemIndicator: Boolean,

        @XStreamAsAttribute
        @XStreamAlias("LAST_MOD_USER")
        var lastModUser: String,

        @XStreamAsAttribute
        @XStreamAlias("LAST_MOD_TIMESTAMP")
        @XStreamConverter(LocalDateTimeConverter::class)
        var lastModTimestamp: LocalDateTime,

        @XStreamAsAttribute
        @XStreamAlias("SYNCSTR")
        var syncstr: String,

        @XStreamAsAttribute
        @XStreamAlias("STATUS")
        @XStreamConverter(StatusConverter::class)
        var status: Status,

        @XStreamAsAttribute
        @XStreamAlias("VERSION")
        var version: Int
) {
    constructor() : this(
            orderChannelOid = "", orderChannelCode = "", description = "",
            sequence = 0, systemIndicator = false,
            lastModUser = "", lastModTimestamp = DateTimeUtil.getCurrentDateTime(), syncstr = "", status = Status.Active, version = 0)
}