package com.exiasoft.itsstaticdata.service

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.service.BaseServiceIntf
import com.exiasoft.itsstaticdata.model.SimpleOperationUnit
import com.exiasoft.itsstaticdata.model.SimpleOperationUnitData
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

interface SimpleOperationUnitService : BaseServiceIntf<String, SimpleOperationUnit> {

    fun find(authenToken: AuthenticationToken, nameOneDefLang: String?, pageable: Pageable): Mono<Page<SimpleOperationUnit>>

}