package com.exiasoft.itsauthen.model.security

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

@XStreamAlias("USER_DATA_ENTITLEMENT")
data class DataEntitlement(

        @XStreamAsAttribute
        @XStreamAlias("USER_OID")
        var userOid: String,

        @XStreamAsAttribute
        @XStreamAlias("ALLOW_SHORT_SELL_APPROVAL_IND")
        @XStreamConverter(BooleanConverter::class)
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var allowShortSellApproval: Boolean,

        @XStreamAsAttribute
        @XStreamAlias("ALL_AE_4_ORDER_APPR_IND")
        @XStreamConverter(BooleanConverter::class)
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var allAeForOrderApproval: Boolean,

        @XStreamAsAttribute
        @XStreamAlias("ALL_BROKER_EXCH_IND")
        @XStreamConverter(BooleanConverter::class)
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var allBrokerExch: Boolean,

        @XStreamAsAttribute
        @XStreamAlias("ALL_EXCH_IND")
        @XStreamConverter(BooleanConverter::class)
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var allExchange: Boolean,

        @XStreamAsAttribute
        @XStreamAlias("APPR_OWN_ORDER_IND")
        @XStreamConverter(BooleanConverter::class)
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var approveOwnedOrder: Boolean,

        @XStreamAsAttribute
        @XStreamAlias("DEFAULT_OPER_UNIT_OID")
        var defaultOperUnitOid: String,

        @XStreamAsAttribute
        @XStreamAlias("CLIENT_OID")
        var clientOid: String?,

        @XStreamAsAttribute
        @XStreamAlias("LAST_MOD_TIMESTAMP")
        @XStreamConverter(LocalDateTimeConverter::class)
        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        var lastModTimestamp: LocalDateTime,

        @XStreamAsAttribute
        @XStreamAlias("LAST_MOD_USER")
        var lastModUser: String,

        @XStreamAsAttribute
        @XStreamAlias("MANAGE_AE_UNDER_ALL_OU_IND")
        @XStreamConverter(BooleanConverter::class)
        @JsonSerialize(using = BooleanSerializer::class)
        @JsonDeserialize(using = BooleanDeserializer::class)
        var manageAeUnderAllOu: Boolean,

        @XStreamAsAttribute
        @XStreamAlias("OPER_UNIT_OID")
        var operUnitOid: String,

        @XStreamAsAttribute
        @XStreamAlias("SYNCSTR")
        var syncstr: String,

        @XStreamAsAttribute
        @XStreamAlias("STATUS")
        @XStreamConverter(StatusConverter::class)
        @JsonSerialize(using = StatusSerializer::class)
        @JsonDeserialize(using = StatusDeserializer::class)
        var status: Status,

        @XStreamAsAttribute @XStreamAlias("VERSION")
        var version: Int,

        @XStreamAsAttribute
        @XStreamAlias("USER_ROLE_OID")
        var userRoleOid: String

) {

    constructor() : this(userOid = "",
            allowShortSellApproval = false, allAeForOrderApproval = false, allBrokerExch = false,
            allExchange = false, approveOwnedOrder = false, defaultOperUnitOid = "",
            clientOid = null, lastModTimestamp = DateTimeUtil.getCurrentDateTime(), lastModUser = "",
            manageAeUnderAllOu = false, operUnitOid = "", syncstr = "", status = Status.Active, version = 1,
            userRoleOid = ""
    )
}