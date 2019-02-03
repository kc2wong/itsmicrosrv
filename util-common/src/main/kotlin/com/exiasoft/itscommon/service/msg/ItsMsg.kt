package com.exiasoft.itscommon.service.msg

import com.thoughtworks.xstream.annotations.XStreamAlias

@XStreamAlias("MSG")
class ItsMsg<T> {
    @XStreamAlias("HEADER")
    var header : ItsMsgHeader? = null

    @XStreamAlias("BODY")
    lateinit var body : ItsMsgBody<T>

    constructor(header: ItsMsgHeader?, body: ItsMsgBody<T>) {
        this.header = header
        this.body = body
    }

    constructor()
}

