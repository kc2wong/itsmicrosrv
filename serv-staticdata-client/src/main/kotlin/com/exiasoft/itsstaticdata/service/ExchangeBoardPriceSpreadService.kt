package com.exiasoft.itsstaticdata.service

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.service.BaseServiceIntf
import com.exiasoft.itsstaticdata.model.ExchangeBoardPriceSpread
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

interface ExchangeBoardPriceSpreadService : BaseServiceIntf<ExchangeBoardPriceSpread.Id, ExchangeBoardPriceSpread> {

    fun find(authenToken: AuthenticationToken, exchangeBoardOid: String?, exchangeBoardPriceSpreadCode: String?, pageable: Pageable): Mono<Page<ExchangeBoardPriceSpread>>

}