package com.exiasoft.itsstaticdata.service.impl.server.demo

import com.exiasoft.itscommon.annotation.ItsFunction
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.bean.XStreamProvider
import com.exiasoft.itscommon.service.server.demo.BaseDemoServiceImpl
import com.exiasoft.itsstaticdata.model.Currency
import com.exiasoft.itsstaticdata.service.CurrencyService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service(value="CurrencyServiceImpl")
@ItsFunction(["StaticDataGrp.Currency.Maintenance", "StaticDataGrp.LookupHelper"])
class CurrencyServiceDemoImpl(
        tokenProvider: TokenProvider,
        xStreamProvider: XStreamProvider
) : BaseDemoServiceImpl<String, Currency>(tokenProvider, xStreamProvider, Currency::class.java), CurrencyService {

    override fun getOid(obj: Currency): String {
        return obj.currencyOid
    }

    override fun getResourceName(): String {
        return "/service/demo/currency.xml"
    }

    override fun isEqualsInIdentifier(id: String, obj: Currency): Boolean {
        return id == obj.currencyCode
    }

    override fun find(authenToken: AuthenticationToken, descptDefLang: String?, pageable: Pageable): Mono<Page<Currency>> {
        var result = data.asSequence().toList()
        result = descptDefLang?.let { result.filter { e -> e.descptDefLang.contains(it, true) }} ?: result
        result = result.sortedWith(createComparator(pageable))
        return Mono.just(getPage(authenToken, result, pageable))
    }
}
