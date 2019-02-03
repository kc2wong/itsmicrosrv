package com.exiasoft.itsauthen.model.security

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute

@XStreamAlias("USER")
data class User(

        @XStreamAsAttribute
        @XStreamAlias("USER_OID")
        var userOid: String,

        @XStreamAsAttribute
        @XStreamAlias("USER_ID")
        var userId: String

) {
    constructor() : this(userOid = "", userId = ""
    )
}