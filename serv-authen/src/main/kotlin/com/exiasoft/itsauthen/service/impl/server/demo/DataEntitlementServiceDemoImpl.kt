package com.exiasoft.itsauthen.service.impl.server.demo

import com.exiasoft.itsauthen.model.security.DataEntitlement
import com.exiasoft.itsauthen.service.DataEntitlementService
import com.exiasoft.itscommon.annotation.ItsFunction
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.bean.XStreamProvider
import com.exiasoft.itscommon.service.server.demo.BaseDemoServiceImpl
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service(value="DataEntitlementServiceImpl")
@ItsFunction(["StaticDataGrp.LookupHelper"])
class DataEntitlementServiceDemoImpl (
        tokenProvider: TokenProvider,
        xStreamProvider: XStreamProvider
) : BaseDemoServiceImpl<String, DataEntitlement>(tokenProvider, xStreamProvider, DataEntitlement::class.java), DataEntitlementService {

    override fun getDataEntitlement(authenToken: AuthenticationToken): Mono<DataEntitlement> {
        return authenToken.getUserOid()?.let { findByOid(authenToken, it) } ?: Mono.empty()
    }

    override fun getOid(obj: DataEntitlement): String {
        return obj.userOid
    }

    override fun getResourceName(): String {
        return "/service/demo/dataEntitlement.xml"
    }

    override fun isEqualsInIdentifier(id: String, obj: DataEntitlement): Boolean {
        return id == obj.userOid
    }

}