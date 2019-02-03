package com.exiasoft.itsauthen.service.impl.server.demo

import com.exiasoft.itsauthen.model.security.DataEntitlement
import com.exiasoft.itsauthen.model.security.User
import com.exiasoft.itsauthen.service.UserProfileService
import com.exiasoft.itscommon.annotation.ItsFunction
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.bean.XStreamProvider
import com.exiasoft.itscommon.service.server.demo.BaseDemoServiceImpl
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserProfileServiceDemoImpl (
        tokenProvider: TokenProvider,
        xStreamProvider: XStreamProvider
) : BaseDemoServiceImpl<String, User>(tokenProvider, xStreamProvider, User::class.java), UserProfileService {

    private lateinit var accessibleFunctionsResponse: Set<String>

    init {
        accessibleFunctionsResponse = loadJson("/service/demo/accessibleFunctions.json")
    }

    override fun getAccessibileFunctions(authenToken: AuthenticationToken): Mono<Set<String>> {
        return Mono.just(accessibleFunctionsResponse)
    }

    override fun getOid(obj: User): String {
        return obj.userOid
    }

    override fun getResourceName(): String {
        return "/service/demo/user.xml"
    }

    override fun isEqualsInIdentifier(id: String, obj: User): Boolean {
        return id == obj.userId
    }

    private fun loadJson(resourceName: String): Set<String> {
        val objectMapper = ObjectMapper()
        val inStream = this.javaClass.getResourceAsStream(resourceName)
        val rtn = objectMapper.readValue(inStream, List::class.java)
        inStream.close()
        return rtn.asSequence().map { v -> v as String }.toSet()
    }

    @ItsFunction(["SystemSecurityGrp.User.Search"])
    override fun getUserById(authenToken: AuthenticationToken, userId: String): Mono<User> {
        return this.findByIdentifier(authenToken, userId)
    }
}