package com.exiasoft.itsaccount.service.impl.server.demo

import com.exiasoft.itsaccount.model.SimpleTradingAccount
import com.exiasoft.itsaccount.service.TradingAccountService
import com.exiasoft.itsauthen.service.DataEntitlementService
import com.exiasoft.itscommon.annotation.ItsFunction
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.bean.XStreamProvider
import com.exiasoft.itscommon.service.server.demo.BaseDemoServiceImpl
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service(value = "TradingAccountServiceImpl")
class TradingAccountServiceDemoImpl (
        tokenProvider: TokenProvider,
        xStreamProvider: XStreamProvider,
        val dataEntitlementService: DataEntitlementService
) : BaseDemoServiceImpl<String, SimpleTradingAccount>(tokenProvider, xStreamProvider, SimpleTradingAccount::class.java), TradingAccountService {

    override fun getOid(obj: SimpleTradingAccount): String {
        return obj.tradingAccountOid
    }

    override fun getResourceName(): String {
        return "/service/demo/tradingAccount.xml"
    }

    override fun isEqualsInIdentifier(id: String, obj: SimpleTradingAccount): Boolean {
        return id == obj.tradingAccountCode
    }

    @ItsFunction(["ClientAccountGrp.ClientAccount.Maintenance", "ClientAccountGrp.ClientAccount.Search"])
    override fun find(authenToken: AuthenticationToken, tradingAccountCode: String?, nameOneDefLang: String?, pageable: Pageable): Mono<Page<SimpleTradingAccount>> {
        var result = data.asSequence().toList()
        result = nameOneDefLang?.let { result.filter { e -> e.nameOneDefLang.contains(it, true) } } ?: result
        result = tradingAccountCode?.let { result.filter { e -> e.tradingAccountCode == it } } ?: result
        dataEntitlementService.getDataEntitlement(authenToken).subscribe { de ->
            result = de.clientOid?.let {
                result.filter { e -> e.clientOid == it }
            } ?: result
            result = result.sortedWith(createComparator(pageable))
        }
        return Mono.just(getPage(authenToken, result, pageable))
    }
}
