package com.exiasoft.itsauthen.service.impl.server.demo

import com.exiasoft.itsauthen.service.AuthenticationService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.net.InetAddress
import java.util.*

@Service
class AuthenticationServiceDemoImpl(): AuthenticationService {

    private lateinit var initiateLoginResponse: Map<String, String>
    private lateinit var authenticateResponse: Map<String, String>

    init {
        initiateLoginResponse = loadJson("/service/demo/initiateLogin.json")
        authenticateResponse = loadJson("/service/demo/authenticate.json")
    }

    override fun initiateLogin(companyCode: String, userid: String, inetAddress: InetAddress, base64Output: Boolean): Mono<AuthenticationService.LoginResponse> {
        return Mono.just(AuthenticationService.LoginResponse(initiateLoginResponse))
    }

    override fun authenticate(jsessionId: String, channelCode: String, token: String, spkUskToken: String, wpkGsk: String, forcelogin: Boolean, ipAddr: InetAddress): Mono<AuthenticationService.AuthenResponse> {
        return Mono.just(AuthenticationService.AuthenResponse(authenticateResponse))
    }

    private fun loadJson(resourceName: String): Map<String, String> {
        val objectMapper = ObjectMapper()
        val inStream = this.javaClass.getResourceAsStream(resourceName)
        val rtn = objectMapper.readValue(inStream, Properties::class.java)
        inStream.close()
        return rtn.map { (k, v) -> k as String to v as String }.toMap()
    }
}