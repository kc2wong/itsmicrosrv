package com.exiasoft.itscommon.authen

import org.springframework.security.core.Authentication

interface AuthenticationToken {
    var idToken: String
    fun validate(): Boolean
    fun getAuthentication(): Authentication?
    fun getUserOid(): String?
}
