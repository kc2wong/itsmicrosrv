package com.exiasoft.itsstaticdata.service.impl.server.demo

import com.exiasoft.itscommon.annotation.ItsFunction
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.bean.XStreamProvider
import com.exiasoft.itscommon.service.server.demo.BaseDemoServiceImpl
import com.exiasoft.itsstaticdata.model.SimpleExchange
import com.exiasoft.itsstaticdata.service.ExchangeService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service(value="ExchangeServiceImpl")
@ItsFunction(["StaticDataGrp.Exchange.Maintenance", "StaticDataGrp.LookupHelper"])
class ExchangeServiceDemoImpl(
        tokenProvider: TokenProvider,
        xStreamProvider: XStreamProvider
) : BaseDemoServiceImpl<String, SimpleExchange>(tokenProvider, xStreamProvider, SimpleExchange::class.java), ExchangeService {

    override fun getResourceName(): String {
        return "/service/demo/exchange.xml"
    }

    override fun isEqualsInIdentifier(id: String, obj: SimpleExchange): Boolean {
        return id == obj.exchangeCode
    }

    override fun getOid(obj: SimpleExchange): String {
        return obj.exchangeOid
    }

    override fun find(authenToken: AuthenticationToken, nameDefLang: String?, pageable: Pageable): Mono<Page<SimpleExchange>> {
        var result = data.asSequence().toList()
        result = nameDefLang?.let { result.filter { e -> e.nameDefLang.contains(it, true) }} ?: result
        result = result.sortedWith(createComparator(pageable))
        return Mono.just(getPage(authenToken, result, pageable))
    }

}