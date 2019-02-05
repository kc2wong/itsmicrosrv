package com.exiasoft.itsstaticdata.service

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.service.BaseServiceIntf
import com.exiasoft.itsstaticdata.model.ExchangeBoard
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

interface ExchangeBoardService : BaseServiceIntf<ExchangeBoard.Id, ExchangeBoard> {

    fun find(authenToken: AuthenticationToken, exchangeOid: String?, exchangeBoardCode: String?, nameDefLang: String?, pageable: Pageable): Mono<Page<ExchangeBoard>>

}