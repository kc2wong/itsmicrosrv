package com.exiasoft.itsmarketdata.mapper

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itsmarketdata.dto.PriceQuoteDto
import com.exiasoft.itsmarketdata.model.PriceQuote
import com.exiasoft.itsstaticdata.service.CurrencyService
import com.exiasoft.itsstaticdata.service.ExchangeBoardService
import com.exiasoft.itsstaticdata.service.SimpleExchangeService
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class PriceQuoteMapper(val currencyService: CurrencyService, val exchangeService: SimpleExchangeService, val exchangeBoardService: ExchangeBoardService) {

    fun modelToDto(authenToken: AuthenticationToken, priceQuote: Mono<PriceQuote>): Mono<PriceQuoteDto> {
        return priceQuote.flatMap { pq ->
            val currencyResult = currencyService.findByOid(authenToken, pq.currencyOid)
            val exchangeResult = exchangeService.findByOid(authenToken, pq.exchangeOid)

            currencyResult.zipWith(exchangeResult).map {
                val ccy = it.t1
                val exch = it.t2

                val priceQuoteDto = PriceQuoteDto()
                BeanUtils.copyProperties(pq, priceQuoteDto)
                ccy?.let { priceQuoteDto.currencyCode = it.currencyCode }
                exch?.let { priceQuoteDto.exchangeCode = it.exchangeCode }
                priceQuoteDto
            }
        }
    }
}