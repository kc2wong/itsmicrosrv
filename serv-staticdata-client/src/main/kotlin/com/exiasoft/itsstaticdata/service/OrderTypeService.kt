package com.exiasoft.itsstaticdata.service

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.service.BaseServiceIntf
import com.exiasoft.itsstaticdata.model.OrderType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

interface OrderTypeService : BaseServiceIntf<String, OrderType> {

    fun find(authenToken: AuthenticationToken, descptDefLang: String?, pageable: Pageable): Mono<Page<OrderType>>

}