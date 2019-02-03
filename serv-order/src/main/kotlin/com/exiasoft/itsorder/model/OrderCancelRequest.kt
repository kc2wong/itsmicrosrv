package com.exiasoft.itsorder.model

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute

@XStreamAlias("ORDER_CANCEL")
data class OrderCancelRequest(

        @XStreamAsAttribute
        @XStreamAlias("ORDER_NO")
        var orderNumber: String,

        @XStreamAsAttribute
        @XStreamAlias("INPUT_SOURCE")
        var channelCode: String

) {
    constructor() : this(
            orderNumber = "", channelCode = ""
    )
}