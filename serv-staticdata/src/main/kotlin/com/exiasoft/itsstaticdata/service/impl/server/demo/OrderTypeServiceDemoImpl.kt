package com.exiasoft.itsstaticdata.service.impl.server.demo

import com.exiasoft.itscommon.annotation.ItsFunction
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.bean.XStreamProvider
import com.exiasoft.itscommon.service.server.demo.BaseDemoServiceImpl
import com.exiasoft.itsstaticdata.model.Currency
import com.exiasoft.itsstaticdata.model.OrderType
import com.exiasoft.itsstaticdata.service.CurrencyService
import com.exiasoft.itsstaticdata.service.OrderTypeService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service(value="OrderTypeServiceImpl")
@ItsFunction(["StaticDataGrp.OrderType.Maintenance", "StaticDataGrp.LookupHelper"])
class OrderTypeServiceDemoImpl(
        tokenProvider: TokenProvider,
        xStreamProvider: XStreamProvider
) : BaseDemoServiceImpl<String, OrderType>(tokenProvider, xStreamProvider, OrderType::class.java), OrderTypeService {

    override fun getOid(obj: OrderType): String {
        return obj.orderTypeOid
    }

    override fun getResourceName(): String {
        return "/service/demo/orderType.xml"
    }

    override fun isEqualsInIdentifier(id: String, obj: OrderType): Boolean {
        return id == obj.orderTypeCode
    }

    override fun find(authenToken: AuthenticationToken, descptDefLang: String?, pageable: Pageable): Mono<Page<OrderType>> {
        var result = data.asSequence().toList()
        result = descptDefLang?.let { result.filter { e -> e.descptDefLang.contains(it, true) }} ?: result
        result = result.sortedWith(createComparator(pageable))
        return Mono.just(getPage(authenToken, result, pageable))
    }
}
