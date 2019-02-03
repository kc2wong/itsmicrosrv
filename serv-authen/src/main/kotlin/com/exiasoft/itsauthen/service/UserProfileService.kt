package com.exiasoft.itsauthen.service

import com.exiasoft.itsauthen.model.security.User
import com.exiasoft.itscommon.authen.AuthenticationToken
import reactor.core.publisher.Mono

interface UserProfileService {

    fun getAccessibileFunctions(authenToken: AuthenticationToken): Mono<Set<String>>

    fun getUserById(authenToken: AuthenticationToken, userId: String): Mono<User>
}