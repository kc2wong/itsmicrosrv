package com.exiasoft.itsstaticdata.service

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.service.BaseServiceIntf
import com.exiasoft.itsstaticdata.model.Exchange
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

interface ExchangeService : BaseServiceIntf<String, Exchange> {

    fun find(authenToken: AuthenticationToken, nameDefLang: String?, pageable: Pageable): Mono<Page<Exchange>>

}