package com.exiasoft.itsauthen.resource

import com.exiasoft.itsauthen.CONTEXT_PATH
import com.exiasoft.itsauthen.CONTEXT_PATH_INTERNAL
import com.exiasoft.itsauthen.config.ApplicationConfig
import com.exiasoft.itsauthen.config.FunctionListConfig
import com.exiasoft.itsauthen.dto.Credential
import com.exiasoft.itsauthen.model.security.DataEntitlement
import com.exiasoft.itsauthen.service.AuthenticationService
import com.exiasoft.itsauthen.service.DataEntitlementService
import com.exiasoft.itsauthen.service.UserProfileService
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.HEADER_AUTHORIZATION
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.util.WebResponseUtil
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.http.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.net.InetAddress
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(CONTEXT_PATH)
class AuthenticationController(
        val loginService: AuthenticationService,
        val userProfileService: UserProfileService,
        val tokenProvider: TokenProvider,
        val applicationProperties: ApplicationConfig
) {

    private val logger = KotlinLogging.logger {}

    @PostMapping("/papi/challenge")
    fun initiateLogin(@RequestBody challenge: Challenge, httpRequest: HttpServletRequest): Mono<ResponseEntity<AuthenticationToken>> {
        logger.info ( "AuthenticationController.login() starts " )
        val ipAddress = httpRequest.remoteAddr
        logger.info ( "LoginController.login(), ipAddress = {}", ipAddress )
        val response = loginService.initiateLogin(applicationProperties.companyCode, challenge.userid, InetAddress.getByName(ipAddress), true)
        return response.map {
            val itsAuthentication = ItsAuthentication(Credential(challenge.userid, "", false))
            val claims = mapOf(
                    "result" to (it.data["result"] ?: "fail"),
                    "type" to (it.data["type"] ?: ""),
                    "spk" to (it.data["spk"] ?: ""),
                    "spkExp" to (it.data["spkExp"] ?: ""),
                    "spkMod" to (it.data["spkMod"] ?: ""),
                    "wpk" to (it.data["wpk"] ?: ""),
                    "wpkExp" to (it.data["wpkExp"] ?: ""),
                    "wpkMod" to (it.data["wpkMod"] ?: ""),
                    "token" to (it.data["token"] ?: ""),
                    "lastLoginDatetime" to (it.data["lastLoginDatetime"] ?: "0"),
                    "loginFailCount" to (it.data["loginFailCount"] ?: "0"),
                    "Set-Cookie" to (it.data["Set-Cookie"] ?: ""))
            val token = tokenProvider.createToken(itsAuthentication, emptySet(), claims, 1000L * 30)
            val httpHeaders = HttpHeaders()
            httpHeaders.add(HEADER_AUTHORIZATION, token.idToken)
            ResponseEntity(token, httpHeaders, HttpStatus.OK)
        }
    }

    @PostMapping("/papi/response")
    fun authenticate(@RequestBody authenRequest: Response, httpRequest: HttpServletRequest): Mono<ResponseEntity<AuthenticationToken>> {
        logger.info ( "AuthenticationController.authenticate() starts " )
        val ipAddress = httpRequest.remoteAddr
        logger.info ( "LoginController.authenticate(), authenRequest = {}, ipAddress = {}", authenRequest, ipAddress )

        var userid = ""
        var spkUskToken = authenRequest.spkUskToken
        httpRequest.getHeader("Authorization")?.let {
            val props = getDecodedJwt(it.substring(7).trim())
            userid = props["sub"] as String
        }

        val response = loginService.authenticate(authenRequest.jsessionId, authenRequest.channelCode, authenRequest.token,
                spkUskToken, authenRequest.wpkGsk, authenRequest.forcelogin, InetAddress.getByName(ipAddress))
        return response.flatMap {
            val data = it.data
            val authentication = ItsAuthentication(Credential(userid, "", false))
            val lifeTime = applicationProperties.jwtTokenValidityInSeconds

            val claims = mapOf("result" to (data["result"] ?: "fail"), "Set-Cookie" to authenRequest.jsessionId)
            val accessibleFunctions = userProfileService.getAccessibileFunctions(tokenProvider.createToken(authentication, null, claims, 1000L * lifeTime))
            accessibleFunctions.map { af ->
                val token = tokenProvider.createToken(authentication, af, claims, 1000L * lifeTime)
                val httpHeaders = HttpHeaders()
                httpHeaders.add(HEADER_AUTHORIZATION, token.idToken)
                ResponseEntity(token, httpHeaders, HttpStatus.OK)
            }
        }
    }

    private fun getDecodedJwt(jwt: String): Map<String, Any> {
        var result = ""

        val parts = jwt.split("[.]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var index = 0
        for (part in parts) {
            if (index >= 2)
                break
            val partAsBytes = part.toByteArray(charset("UTF-8"))
            val decodedPart = String(java.util.Base64.getUrlDecoder().decode(partAsBytes))
            if (index == 1) {
                result += decodedPart
            }
            index++
        }
        val typeRef = object : TypeReference<java.util.HashMap<String, Any>>() {}
        val om = ObjectMapper()
        return om.readValue(result, typeRef)
    }

    class Challenge() {
        lateinit var userid: String
    }

    class Response() {
        lateinit var jsessionId: String
        lateinit var channelCode: String
        lateinit var token: String
        lateinit var uskToken: String
        lateinit var spkUskToken: String
        lateinit var wpkGsk: String
        var forcelogin: Boolean = true
        var rememberMe: Boolean = false
        var password: String? = null

        override fun toString(): String {
            return "AuthenRequest(jsessionId='$jsessionId', channelCode='$channelCode', token='$token', uskToken='$uskToken', spkUskToken='$spkUskToken', wpkGsk='$wpkGsk', forcelogin=$forcelogin)"
        }
    }

    class ItsAuthentication(val credential: Credential) : Authentication {

        override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
            return HashSet()
        }

        override fun setAuthenticated(isAuthenticated: Boolean) {
        }

        override fun getName(): String {
            return credential.userid
        }

        override fun getCredentials(): Any {
            return credential
        }

        override fun getPrincipal(): Any {
            return credential
        }

        override fun isAuthenticated(): Boolean {
            return true
        }

        override fun getDetails(): Any {
            val rtn = HashMap<String, String>()
            rtn["sessionId"] = "cb549f58c7a1249d5927e9bd657484e4"
            return rtn
        }

        fun getSessionId() : String {
            return "cb549f58c7a1249d5927e9bd657484e4"
        }
    }
}


@RestController
@RequestMapping(CONTEXT_PATH_INTERNAL)
class AuthenticationIntController(
        val tokenProvider: TokenProvider,
        val applicationProperties: ApplicationConfig,
        val functionListConfig: FunctionListConfig,
        val userProfileService: UserProfileService,
        val dataEntitlementService: DataEntitlementService
) {

    private val logger = KotlinLogging.logger {}

    @PostMapping("/papi/authen-token")
    fun createToken(@RequestBody content: Map<String, String>
    ): Mono<String> {
        logger.info ( "AuthenticationIntController.authenticate() - starts" )
        val lifeTime = applicationProperties.jwtTokenValidityInSeconds
        val token = tokenProvider.createToken(UsernamePasswordAuthenticationToken(content["userid"] ?: "", ""),
                functionListConfig.list.toSet(), emptyMap(), 1000L * lifeTime)
        return userProfileService.getUserById(token, content["userid"] ?: "").map {
            val claims = mapOf("userOid" to it.userOid)
            tokenProvider.createToken(UsernamePasswordAuthenticationToken(content["userid"] ?: "", ""),
                    functionListConfig.list.toSet(), claims, 1000L * lifeTime).idToken
        }.switchIfEmpty(Mono.just(token.idToken))
    }

    @GetMapping("/sapi/data-entitlement")
    fun getDataEntitlement(authenticationToken: AuthenticationToken): Mono<ResponseEntity<DataEntitlement>> {
        logger.info ( "AuthenticationIntController.getDataEntitlement() - starts" )
        val dataEntitlement = dataEntitlementService.getDataEntitlement(authenticationToken)
        return WebResponseUtil.wrapOrNotFound(dataEntitlement)
    }

}