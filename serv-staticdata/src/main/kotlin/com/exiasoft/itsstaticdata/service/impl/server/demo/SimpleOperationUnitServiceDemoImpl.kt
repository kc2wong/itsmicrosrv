package com.exiasoft.itsstaticdata.service.impl.server.demo

import com.exiasoft.itscommon.annotation.ItsFunction
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.bean.XStreamProvider
import com.exiasoft.itscommon.service.server.demo.BaseDemoServiceImpl
import com.exiasoft.itscommon.service.server.demo.BaseSimpleObjectDemoServiceImpl
import com.exiasoft.itsstaticdata.model.SimpleOperationUnit
import com.exiasoft.itsstaticdata.model.SimpleOperationUnitData
import com.exiasoft.itsstaticdata.service.SimpleOperationUnitService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service(value = "SimpleOperationUnitServiceImpl")
@ItsFunction(["StaticDataGrp.OperationUnit.Maintenance", "StaticDataGrp.LookupHelper"])
class SimpleOperationUnitServiceDemoImpl(
        tokenProvider: TokenProvider,
        xStreamProvider: XStreamProvider
) : BaseSimpleObjectDemoServiceImpl<String, SimpleOperationUnit, SimpleOperationUnitData>(tokenProvider, xStreamProvider, SimpleOperationUnitData::class.java), SimpleOperationUnitService {

    override fun getOid(obj: SimpleOperationUnit): String {
        return obj.operationUnitOid
    }

    override fun getResourceName(): String {
        return "/service/demo/operationUnit.xml"
    }

    override fun isEqualsInIdentifier(id: String, obj: SimpleOperationUnit): Boolean {
        return id == obj.operationUnitCode
    }

    override fun find(authenToken: AuthenticationToken, nameOneDefLang: String?, pageable: Pageable): Mono<Page<SimpleOperationUnit>> {
        var result = data.asSequence().toList()
        result = nameOneDefLang?.let { result.filter { e -> e.nameOneDefLang.contains(it, true) } } ?: result
        result = result.sortedWith(createComparator(pageable))
        return Mono.just(getPage(authenToken, result, pageable))
    }

}
