package com.exiasoft.itsstaticdata.mapper

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.util.PageUtil
import com.exiasoft.itsstaticdata.dto.ExchangeBoardPriceSpreadDetailDto
import com.exiasoft.itsstaticdata.dto.ExchangeBoardPriceSpreadDto
import com.exiasoft.itsstaticdata.dto.InstrumentDto
import com.exiasoft.itsstaticdata.model.ExchangeBoard
import com.exiasoft.itsstaticdata.model.ExchangeBoardPriceSpread
import com.exiasoft.itsstaticdata.model.Instrument
import com.exiasoft.itsstaticdata.service.ExchangeBoardService
import com.exiasoft.itsstaticdata.service.ExchangeService
import mu.KotlinLogging
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
//class ExchangeMapper(val operationUnitService: CurrencyService, val exchangeParameterMapper: ExchangeParameterMapper, val exchangeOrderTypeMapper: ExchangeOrderTypeMapper) {
class ExchangeBoardPriceSpreadMapper(val exchangeService: ExchangeService, val exchangeBoardService: ExchangeBoardService) {

    private val logger = KotlinLogging.logger {}

    fun modelToDto(authenToken: AuthenticationToken, exchBoardPriceSpreads: Flux<ExchangeBoardPriceSpread>): Flux<ExchangeBoardPriceSpreadDto> {

        return exchangeBoardService.findAll(authenToken, PageUtil.unlimit()).flatMapMany { exchangeBoardResult ->

            val exchBoardMap = exchangeBoardResult.map { exchBoard -> exchBoard.exchangeBoardOid to exchBoard }.toMap()

            exchBoardPriceSpreads.map { exchangeBoardPriceSpread ->
                val exchBoardPriceSpreadDto = ExchangeBoardPriceSpreadDto()
                BeanUtils.copyProperties(exchangeBoardPriceSpread, exchBoardPriceSpreadDto)
                exchBoardMap[exchangeBoardPriceSpread.exchangeBoardOid] ?.let { exchBoardPriceSpreadDto.exchangeBoardCode = it.exchangeBoardCode }
                exchBoardPriceSpreadDto.exchangeBoardPriceSpreadDetail = exchangeBoardPriceSpread.exchangeBoardPriceSpreadDetail.map {
                    val exchBoardPriceSpreadDetailDto = ExchangeBoardPriceSpreadDetailDto()
                    BeanUtils.copyProperties(it, exchBoardPriceSpreadDetailDto)
                    exchBoardPriceSpreadDetailDto
                }.toList()
                exchBoardPriceSpreadDto
            }
        }
    }

    fun modelToDto(authenToken: AuthenticationToken, exchangeBoards: List<ExchangeBoardPriceSpread>): Flux<ExchangeBoardPriceSpreadDto> {
        return modelToDto(authenToken, Flux.fromIterable(exchangeBoards))
    }

    fun modelToDto(authenToken: AuthenticationToken, instrument: Mono<ExchangeBoardPriceSpread>): Mono<ExchangeBoardPriceSpreadDto> {
        return modelToDto(authenToken, instrument.flatMapMany { Mono.just(it) }).last()
    }

//    fun modelToDto(authenToken: AuthenticationToken, exchangeBoardPriceSpread: ExchangeBoardPriceSpread, exchangeBoard: ExchangeBoard): ExchangeBoardPriceSpreadDto {
//        val exchBoardPriceSpreadDto = ExchangeBoardPriceSpreadDto()
//        BeanUtils.copyProperties(exchangeBoardPriceSpread, exchBoardPriceSpreadDto)
//        exchBoardPriceSpreadDto.exchangeBoardCode = exchangeBoard.exchangeBoardCode
//        exchBoardPriceSpreadDto.exchangeBoardPriceSpreadDetail = exchangeBoardPriceSpread.exchangeBoardPriceSpreadDetail.map {
//            val exchBoardPriceSpreadDetailDto = ExchangeBoardPriceSpreadDetailDto()
//            BeanUtils.copyProperties(it, exchBoardPriceSpreadDetailDto)
//            exchBoardPriceSpreadDetailDto
//        }.toList()
//        return exchBoardPriceSpreadDto
//    }

//    fun modelToDto(authenToken: AuthenticationToken, exchangeBoardPriceSpread: Mono<ExchangeBoardPriceSpread>, exchange: ExchangeBoard?): Mono<ExchangeBoardPriceSpreadDto> {
//        return exchangeBoardPriceSpread.flatMap { exchBoardPriceSpread ->
//            val exchBoardPriceSpreadDto = ExchangeBoardPriceSpreadDto()
//            BeanUtils.copyProperties(exchBoardPriceSpread, exchBoardPriceSpreadDto)
//            exchBoardPriceSpreadDto.exchangeBoardPriceSpreadDetail = exchBoardPriceSpread.exchangeBoardPriceSpreadDetail.map { exchBoardPriceSpreadDtl ->
//                val exchBoardPriceSpreadDetailDto = ExchangeBoardPriceSpreadDetailDto()
//                BeanUtils.copyProperties(exchBoardPriceSpreadDtl, exchBoardPriceSpreadDetailDto)
//                exchBoardPriceSpreadDetailDto
//            }.toList()
//            exchangeBoardService.findByOid(authenToken, exchBoardPriceSpread.exchangeBoardOid).map { exchBoard ->
//                exchBoardPriceSpreadDto.exchangeBoardCode = exchBoard.exchangeBoardCode
//                exchBoardPriceSpreadDto
//            }.defaultIfEmpty(exchBoardPriceSpreadDto)
//        }
//    }

//    fun modelToDto(authenToken: AuthenticationToken, exchangeBoardPriceSpread: ExchangeBoardPriceSpread, exchangeBoard: ExchangeBoard?): ExchangeBoardPriceSpreadDto {
//        return modelToDto(authenToken, Mono.just(exchangeBoardPriceSpread), exchangeBoard).block()!!
//    }

}