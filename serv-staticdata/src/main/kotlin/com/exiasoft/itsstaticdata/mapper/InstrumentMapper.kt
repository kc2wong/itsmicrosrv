package com.exiasoft.itsstaticdata.mapper

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.util.PageUtil
import com.exiasoft.itsstaticdata.dto.InstrumentDto
import com.exiasoft.itsstaticdata.model.*
import com.exiasoft.itsstaticdata.service.CurrencyService
import com.exiasoft.itsstaticdata.service.ExchangeBoardPriceSpreadService
import com.exiasoft.itsstaticdata.service.ExchangeBoardService
import com.exiasoft.itsstaticdata.service.ExchangeService
import mu.KotlinLogging
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class InstrumentMapper(val currencyService: CurrencyService, val exchangeService: ExchangeService, val exchangeBoardService: ExchangeBoardService, val exchangeBoardPriceSpreadService: ExchangeBoardPriceSpreadService) {

    private val logger = KotlinLogging.logger {}

    fun modelToDto(authenToken: AuthenticationToken, instruments: Flux<Instrument>): Flux<InstrumentDto> {

        return currencyService.findAll(authenToken, PageUtil.unlimit()).zipWith(
                exchangeBoardService.findAll(authenToken, PageUtil.unlimit())).zipWith(
                exchangeService.findAll(authenToken, PageUtil.unlimit())).flatMapMany {

            val exchMap = it.t2.content.map { exchange -> exchange.exchangeOid to exchange }.toMap()
            val exchBoardMap = it.t1.t2.map { exchBoard -> exchBoard.exchangeBoardOid to exchBoard }.toMap()
            val ccyMap = it.t1.t1.map { currency -> currency.currencyOid to currency }.toMap()

            instruments.flatMap {instrument ->
                exchangeBoardPriceSpreadService.findByOid(authenToken, instrument.exchangeBoardPriceSpreadOid).map { exchPs ->
                    val instrumentDto = InstrumentDto()
                    BeanUtils.copyProperties(instrument, instrumentDto)

                    ccyMap[instrument.tradingCurrencyOid]?.let { instrumentDto.tradingCurrencyCode = it.currencyCode }
                    exchBoardMap[instrument.exchangeBoardOid]?.let {
                        instrumentDto.exchangeBoardCode = it.exchangeBoardCode
                        exchMap[it.exchangeOid]?.let { instrumentDto.exchangeCode = it.exchangeCode }
                    }
                    instrumentDto.exchangeBoardPsCode = exchPs.exchangeBoardPriceSpreadCode
                    instrumentDto
                }
            }
        }

    }

    fun modelToDto(authenToken: AuthenticationToken, instruments: List<Instrument>): Flux<InstrumentDto> {
        return modelToDto(authenToken, Flux.fromIterable(instruments))
    }

    fun modelToDto(authenToken: AuthenticationToken, instrument: Mono<Instrument>): Mono<InstrumentDto> {
        return modelToDto(authenToken, instrument.flatMapMany { Mono.just(it) }).last()
    }

//    fun modelToDto(authenToken: AuthenticationToken, instrument: Instrument): InstrumentDto {
//        return modelToDto(authenToken, Mono.just(instrument)).block()!!
//    }

}