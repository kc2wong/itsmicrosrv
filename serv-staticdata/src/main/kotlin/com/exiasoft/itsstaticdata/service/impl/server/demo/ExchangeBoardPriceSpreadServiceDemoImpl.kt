package com.exiasoft.itsstaticdata.service.impl.server.demo

import com.exiasoft.itscommon.annotation.ItsFunction
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.bean.XStreamProvider
import com.exiasoft.itscommon.service.server.demo.BaseDemoServiceImpl
import com.exiasoft.itsstaticdata.model.ExchangeBoardPriceSpread
import com.exiasoft.itsstaticdata.model.ExchangeBoardPriceSpreadDetail
import com.exiasoft.itsstaticdata.service.ExchangeBoardPriceSpreadService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service(value="ExchangeBoardPriceSpreadServiceImpl")
@ItsFunction(["StaticDataGrp.ExchBrdPriceSpread.Maintenance", "StaticDataGrp.LookupHelper"])
class ExchangeBoardPriceSpreadServiceDemoImpl(
        tokenProvider: TokenProvider,
        xStreamProvider: XStreamProvider
) : BaseDemoServiceImpl<ExchangeBoardPriceSpread.Id, ExchangeBoardPriceSpread>(tokenProvider, xStreamProvider, ExchangeBoardPriceSpread::class.java, listOf(ExchangeBoardPriceSpreadDetail::class.java)), ExchangeBoardPriceSpreadService {

    override fun getResourceName(): String {
        return "/service/demo/exchangeBoardPriceSpread.xml"
    }

    override fun getOid(obj: ExchangeBoardPriceSpread): String {
        return obj.exchangeBoardPriceSpreadOid
    }

    override fun isEqualsInIdentifier(id: ExchangeBoardPriceSpread.Id, obj: ExchangeBoardPriceSpread): Boolean {
        return id.exchangeBoardOid == obj.exchangeBoardOid && id.exchangeBoardPriceSpreadCode == obj.exchangeBoardPriceSpreadCode
    }

    override fun find(authenToken: AuthenticationToken, exchangeBoardOid: String?, exchangeBoardPriceSpreadCode: String?, pageable: Pageable): Mono<Page<ExchangeBoardPriceSpread>> {
        var result = data.asSequence().toList()
        result = exchangeBoardOid?.let { result.filter { e -> e.exchangeBoardOid == it } } ?: result
        result = exchangeBoardPriceSpreadCode?.let { result.filter { e -> e.exchangeBoardPriceSpreadCode == it } } ?: result
        result = result.sortedWith(createComparator(pageable))

        return Mono.just(getPage(authenToken, result, pageable))
    }

}