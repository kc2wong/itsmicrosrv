package com.exiasoft.itsstaticdata.service

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.model.enumaration.Status
import com.exiasoft.itscommon.service.BaseServiceIntf
import com.exiasoft.itsstaticdata.model.Instrument
import com.exiasoft.itsstaticdata.model.SimpleStock
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

interface SimpleStockService : BaseServiceIntf<Instrument.Id, SimpleStock> {

    fun find(authenToken: AuthenticationToken, exchangeBoardOid: Set<String>?, instrumentCode: String?, nameDefLang: String?, status: Status?, pageable: Pageable): Mono<Page<SimpleStock>>

}