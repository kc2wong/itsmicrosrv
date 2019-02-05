package com.exiasoft.itsstaticdata.service

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.service.BaseServiceIntf
import com.exiasoft.itsstaticdata.model.Currency
import com.exiasoft.itsstaticdata.model.TradingAccountType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

interface TradingAccountTypeService : BaseServiceIntf<String, TradingAccountType> {

    fun find(authenToken: AuthenticationToken, description: String?, pageable: Pageable): Mono<Page<TradingAccountType>>

}