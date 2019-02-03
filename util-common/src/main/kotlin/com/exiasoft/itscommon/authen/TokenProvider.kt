package com.exiasoft.itscommon.authen

import org.springframework.security.core.Authentication

const val HEADER_AUTHORIZATION = "Authorization"

interface TokenProvider {

    fun createToken(authentication: Authentication, groupIds: Set<String>?, claims: Map<String, String>?, lifetime: Long): AuthenticationToken

    fun createToken(tokenStr: String): AuthenticationToken

/*
    fun getAuthentication(authToken: String?): Authentication?

    fun validateToken(authToken: String?): Boolean

    fun getClaim(authToken: String?, key: String): String?
*/

}