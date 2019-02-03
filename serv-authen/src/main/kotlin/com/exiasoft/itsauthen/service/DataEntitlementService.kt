package com.exiasoft.itsauthen.service

import com.exiasoft.itsauthen.model.security.DataEntitlement
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.TokenProvider
import reactor.core.publisher.Mono

interface DataEntitlementService {

    fun getDataEntitlement(authenToken: AuthenticationToken): Mono<DataEntitlement>

}