package com.exiasoft.itsstaticdata.service.impl.server.demo

import com.exiasoft.itscommon.annotation.ItsFunction
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.bean.XStreamProvider
import com.exiasoft.itscommon.service.server.demo.BaseDemoServiceImpl
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
) : SimpleOperationUnitService {

    val delegate = SimpleOperationUnitDataServiceDemoImpl(tokenProvider, xStreamProvider)

    override fun find(authenToken: AuthenticationToken, nameOneDefLang: String?, pageable: Pageable): Mono<Page<SimpleOperationUnit>> {
        return delegate.find(authenToken, nameOneDefLang, pageable)
                .map { PageImpl(it.content.asSequence().map { it as SimpleOperationUnit }.toList(), pageable, it.totalElements) }
    }

    override fun findAll(authenToken: AuthenticationToken, pageable: Pageable): Mono<Page<SimpleOperationUnit>> {
        return delegate.findAll(authenToken, pageable)
                .map { PageImpl(it.content.asSequence().map { it as SimpleOperationUnit }.toList(), pageable, it.totalElements) }
    }

    override fun findByIdentifier(authenToken: AuthenticationToken, id: String): Mono<SimpleOperationUnit> {
        return delegate.findByIdentifier(authenToken, id).map { it as SimpleOperationUnit }
    }

    override fun findByOid(authenToken: AuthenticationToken, oid: String): Mono<SimpleOperationUnit> {
        return delegate.findByOid(authenToken, oid).map { it as SimpleOperationUnit }
    }

    override fun findByOids(authenToken: AuthenticationToken, oids: Set<String>): Mono<Map<String, SimpleOperationUnit>> {
        return delegate.findByOids(authenToken, oids).map { it.mapValues { v -> v.value as SimpleOperationUnit } }
    }

    @ItsFunction(["StaticDataGrp.OperationUnit.Maintenance", "StaticDataGrp.LookupHelper"])
    class SimpleOperationUnitDataServiceDemoImpl(
            tokenProvider: TokenProvider,
            xStreamProvider: XStreamProvider
    ) : BaseDemoServiceImpl<String, SimpleOperationUnitData>(tokenProvider, xStreamProvider, SimpleOperationUnitData::class.java) {

        override fun getOid(obj: SimpleOperationUnitData): String {
            return obj.operationUnitOid
        }

        override fun getResourceName(): String {
            return "/service/demo/operationUnit.xml"
        }

        override fun isEqualsInIdentifier(id: String, obj: SimpleOperationUnitData): Boolean {
            return id == obj.operationUnitCode
        }

        fun find(authenToken: AuthenticationToken, nameOneDefLang: String?, pageable: Pageable): Mono<Page<SimpleOperationUnitData>> {
            var result = data.asSequence().toList()
            result = nameOneDefLang?.let { result.filter { e -> e.nameOneDefLang.contains(it, true) } } ?: result
            result = result.sortedWith(createComparator(pageable))
            return Mono.just(getPage(authenToken, result, pageable))
        }
    }

}
