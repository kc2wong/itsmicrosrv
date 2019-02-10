package com.exiasoft.itsstaticdata.service

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.service.BaseSimpleObjectServiceIntf
import com.exiasoft.itsstaticdata.model.SimpleExchange
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

interface SimpleExchangeService : BaseSimpleObjectServiceIntf<String, SimpleExchange> {

    fun find(authenToken: AuthenticationToken, nameDefLang: String?, pageable: Pageable): Mono<Page<SimpleExchange>>

}