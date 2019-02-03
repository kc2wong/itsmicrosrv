package com.exiasoft.itsstaticdata.mapper

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itsstaticdata.dto.SimpleExchangeDto
import com.exiasoft.itsstaticdata.model.Currency
import com.exiasoft.itsstaticdata.model.SimpleExchange
import com.exiasoft.itsstaticdata.service.CurrencyService
import mu.KotlinLogging
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ExchangeMapper(val currencyService: CurrencyService) {

    private val logger = KotlinLogging.logger {}

    fun modelToDto(authenToken: AuthenticationToken, exchanges: Flux<SimpleExchange>): Flux<SimpleExchangeDto> {
        return exchanges.collectList().flatMap {exchList ->
            currencyService.findByOids(authenToken, exchList.asSequence().map { exch -> exch.baseCurrencyOid }.toSet()).map { ccyMap ->
                exchList.map { exch ->
                    val exchangeDto = SimpleExchangeDto()
                    BeanUtils.copyProperties(exch, exchangeDto)
                    ccyMap!![exch.baseCurrencyOid] ?.let { ccy -> exchangeDto.baseCurrencyCode = ccy.currencyCode }
                    exchangeDto
                }
            }
        }.flatMapIterable { it }
    }

    fun modelToDto(authenToken: AuthenticationToken, exchanges: List<SimpleExchange>): Flux<SimpleExchangeDto> {
        return modelToDto(authenToken, Flux.fromIterable(exchanges))
    }

    fun modelToDto(authenToken: AuthenticationToken, exchange: Mono<SimpleExchange>, currency: Currency?): Mono<SimpleExchangeDto> {
        return exchange.flatMap { exch ->
            val exchangeDto = SimpleExchangeDto()
            BeanUtils.copyProperties(exch, exchangeDto)
            currencyService.findByOid(authenToken, exch.baseCurrencyOid).map { ccy ->
                exchangeDto.baseCurrencyCode = ccy.currencyCode
                exchangeDto
            }.defaultIfEmpty(exchangeDto)
        }
    }

    fun modelToDto(authenToken: AuthenticationToken, exchange: SimpleExchange, currency: Currency?): SimpleExchangeDto {
        return modelToDto(authenToken, Mono.just(exchange), currency).block()!!
    }

}