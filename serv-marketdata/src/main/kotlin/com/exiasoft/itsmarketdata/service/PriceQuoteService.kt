package com.exiasoft.itsmarketdata.service

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itsmarketdata.model.PriceQuote
import com.exiasoft.itsstaticdata.model.Instrument
import reactor.core.publisher.Mono

interface PriceQuoteService {

    fun findByIdentifier(authenToken: AuthenticationToken, id: Instrument.Id): Mono<PriceQuote>

}