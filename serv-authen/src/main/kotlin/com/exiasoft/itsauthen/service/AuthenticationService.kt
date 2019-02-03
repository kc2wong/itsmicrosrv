package com.exiasoft.itsauthen.service

import reactor.core.publisher.Mono
import java.net.InetAddress

interface AuthenticationService {

    fun initiateLogin(companyCode: String, userid: String, inetAddress: InetAddress, base64Output: Boolean): Mono<LoginResponse>

    fun authenticate(jsessionId: String, channelCode: String, token: String, spkUskToken: String,
                     wpkGsk: String, forcelogin: Boolean, ipAddr: InetAddress): Mono<AuthenResponse>

    data class LoginResponse(val data: Map<String, String>) {
    }

    data class AuthenResponse(val data: Map<String, String>) {
    }

}