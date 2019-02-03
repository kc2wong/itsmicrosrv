package com.exiasoft.itsaccount.service

import com.exiasoft.itsaccount.model.SimpleTradingAccount
import com.exiasoft.itscommon.authen.AuthenticationToken
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

interface TradingAccountService {

    fun find(authenToken: AuthenticationToken, tradingAccountCode: String?, nameOneDefLang: String?, pageable: Pageable): Mono<Page<SimpleTradingAccount>>

}