package com.exiasoft.itsstaticdata.service.impl.server.demo

import com.exiasoft.itscommon.annotation.ItsFunction
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.bean.XStreamProvider
import com.exiasoft.itscommon.model.enumaration.Status
import com.exiasoft.itscommon.service.server.demo.BaseDemoServiceImpl
import com.exiasoft.itsstaticdata.model.Instrument
import com.exiasoft.itsstaticdata.model.SimpleStock
import com.exiasoft.itsstaticdata.service.SimpleStockService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service(value="SimpleStockServiceImpl")
@ItsFunction(["StaticDataGrp.Instrument.Maintenance", "StaticDataGrp.LookupHelper"])
class SimpleStockServiceDemoImpl(
        tokenProvider: TokenProvider,
        xStreamProvider: XStreamProvider
) : BaseDemoServiceImpl<Instrument.Id, SimpleStock>(tokenProvider, xStreamProvider, SimpleStock::class.java), SimpleStockService {

    override fun getResourceName(): String {
        return "/service/demo/stock.xml"
    }

    override fun getOid(obj: SimpleStock): String {
        return obj.instrumentOid
    }

    override fun isEqualsInIdentifier(id: Instrument.Id, obj: SimpleStock): Boolean {
        return id.exchangeBoardOid == obj.exchangeBoardOid && id.instrumentCode == obj.instrumentCode
    }

    override fun find(authenToken: AuthenticationToken, exchangeBoardOid: Set<String>?, instrumentCode: String?, nameDefLang: String?, status: Status?, pageable: Pageable): Mono<Page<SimpleStock>> {
        var result = data.asSequence().toList()
        result = exchangeBoardOid?.let { it.flatMap { result.filter { e -> it.contains(e.exchangeBoardOid) } } } ?: result
        result = instrumentCode?.let { result.filter { e -> e.instrumentCode == it } } ?: result
        result = nameDefLang?.let { result.filter { e -> e.nameDefLang == it } } ?: result
        result = status?.let { result.filter { e -> e.status == it } } ?: result
        result = result.sortedWith(createComparator(pageable))
        return Mono.just(getPage(authenToken, result, pageable))
   }
}