package com.exiasoft.itsstaticdata.service.impl.server.demo

import com.exiasoft.itscommon.annotation.ItsFunction
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.bean.XStreamProvider
import com.exiasoft.itscommon.service.server.demo.BaseDemoServiceImpl
import com.exiasoft.itsstaticdata.model.TradingAccountType
import com.exiasoft.itsstaticdata.service.TradingAccountTypeService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service(value="TradingAccountTypeServiceImpl")
@ItsFunction(["StaticDataGrp.CaccType.Maintenance", "StaticDataGrp.LookupHelper"])
class TradingAccountTypeServiceDemoImpl(
        tokenProvider: TokenProvider,
        xStreamProvider: XStreamProvider
) : BaseDemoServiceImpl<String, TradingAccountType>(tokenProvider, xStreamProvider, TradingAccountType::class.java), TradingAccountTypeService {

    override fun getOid(obj: TradingAccountType): String {
        return obj.tradingAccountTypeOid
    }

    override fun getResourceName(): String {
        return "/service/demo/tradingAccountType.xml"
    }

    override fun isEqualsInIdentifier(id: String, obj: TradingAccountType): Boolean {
        return id == obj.tradingAccountTypeCode
    }

    override fun find(authenToken: AuthenticationToken, description: String?, pageable: Pageable): Mono<Page<TradingAccountType>> {
        var result = data.asSequence().toList()
        result = description?.let { result.filter { e -> e.description.contains(it, true) }} ?: result
        result = result.sortedWith(createComparator(pageable))
        return Mono.just(getPage(authenToken, result, pageable))
    }
}
