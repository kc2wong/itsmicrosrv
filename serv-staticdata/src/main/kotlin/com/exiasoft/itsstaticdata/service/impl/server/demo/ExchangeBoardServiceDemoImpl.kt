package com.exiasoft.itsstaticdata.service.impl.server.demo

import com.exiasoft.itscommon.annotation.ItsFunction
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.bean.XStreamProvider
import com.exiasoft.itscommon.service.server.demo.BaseDemoServiceImpl
import com.exiasoft.itsstaticdata.model.ExchangeBoard
import com.exiasoft.itsstaticdata.service.ExchangeBoardService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service(value="ExchangeBoardServiceImpl")
@ItsFunction(["StaticDataGrp.ExchBoard.Maintenance", "StaticDataGrp.LookupHelper"])
class ExchangeBoardServiceDemoImpl(
        tokenProvider: TokenProvider,
        xStreamProvider: XStreamProvider
) : BaseDemoServiceImpl<ExchangeBoard.Id, ExchangeBoard>(tokenProvider, xStreamProvider, ExchangeBoard::class.java), ExchangeBoardService {

    override fun getResourceName(): String {
        return "/service/demo/exchangeBoard.xml"
    }

    override fun getOid(obj: ExchangeBoard): String {
        return obj.exchangeBoardOid
    }

    override fun isEqualsInIdentifier(id: ExchangeBoard.Id, obj: ExchangeBoard): Boolean {
        return id.exchangeOid == obj.exchangeOid && id.exchangeBoardCode == obj.exchangeBoardCode
    }

    override fun find(authenToken: AuthenticationToken, exchangeOid: String?, exchangeBoardCode: String?, nameDefLang: String?, pageable: Pageable): Mono<Page<ExchangeBoard>> {
        var result = data.asSequence().toList()
        result = exchangeOid?.let { result.filter { e -> e.exchangeOid == it }} ?: result
        result = exchangeBoardCode?.let { result.filter { e -> e.exchangeBoardCode == it }} ?: result
        result = nameDefLang?.let { result.filter { e -> e.nameDefLang == it }} ?: result
        result = result.sortedWith(createComparator(pageable))
        return Mono.just(getPage(authenToken, result, pageable))
    }
}