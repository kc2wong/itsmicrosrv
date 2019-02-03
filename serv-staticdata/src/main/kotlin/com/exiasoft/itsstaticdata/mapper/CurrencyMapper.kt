package com.exiasoft.itsstaticdata.mapper

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itsstaticdata.dto.CurrencyDto
import com.exiasoft.itsstaticdata.dto.SimpleExchangeDto
import com.exiasoft.itsstaticdata.model.Currency
import com.exiasoft.itsstaticdata.model.SimpleExchange
import com.exiasoft.itsstaticdata.service.CurrencyService
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class CurrencyMapper {

    fun modelToDto(authenToken: AuthenticationToken, currencies: Flux<Currency>): Flux<CurrencyDto> {
        return currencies.map {
            modelToDto(authenToken, it)
        }
    }

    fun modelToDto(authenToken: AuthenticationToken, currencies: List<Currency>): Flux<CurrencyDto> {
        return Flux.fromIterable(currencies.map {
            modelToDto(authenToken, it)
        })
    }

    fun modelToDto(authenToken: AuthenticationToken, currency: Mono<Currency>): Mono<CurrencyDto> {
        return currency.map {
            modelToDto(authenToken, it)
        }
    }

    fun modelToDto(authenToken: AuthenticationToken, currency: Currency): CurrencyDto {
        val currencyDto = CurrencyDto()
        BeanUtils.copyProperties(currency, currencyDto)
        return currencyDto
    }

}