package com.exiasoft.itsstaticdata.mapper

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itsstaticdata.dto.ExchangeBoardPriceSpreadDetailDto
import com.exiasoft.itsstaticdata.dto.ExchangeBoardPriceSpreadDto
import com.exiasoft.itsstaticdata.model.ExchangeBoard
import com.exiasoft.itsstaticdata.model.ExchangeBoardPriceSpread
import com.exiasoft.itsstaticdata.service.ExchangeBoardService
import mu.KotlinLogging
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
//class ExchangeMapper(val operationUnitService: CurrencyService, val exchangeParameterMapper: ExchangeParameterMapper, val exchangeOrderTypeMapper: ExchangeOrderTypeMapper) {
class ExchangeBoardPriceSpreadMapper(val exchangeBoardService: ExchangeBoardService) {

    private val logger = KotlinLogging.logger {}

    fun modelToDto(authenToken: AuthenticationToken, exchBoardPriceSpreads: Flux<ExchangeBoardPriceSpread>): Flux<ExchangeBoardPriceSpreadDto> {
        return exchBoardPriceSpreads.collectList().flatMap { exchBoardPriceSpreadList ->
            exchangeBoardService.findByOids(authenToken, exchBoardPriceSpreadList.asSequence().map { exchBoardPriceSpread -> exchBoardPriceSpread.exchangeBoardOid }.toSet())
                    .defaultIfEmpty(emptyMap()).map { exchBoardMap ->
                        exchBoardPriceSpreadList.map { exchBoardPriceSpread ->
                            val exchBoardPriceSpreadDto = ExchangeBoardPriceSpreadDto()
                            exchBoardPriceSpreadDto.exchangeBoardPriceSpreadDetail = exchBoardPriceSpread.exchangeBoardPriceSpreadDetail.map { exchBoardPriceSpreadDtl ->
                                val exchBoardPriceSpreadDetailDto = ExchangeBoardPriceSpreadDetailDto()
                                BeanUtils.copyProperties(exchBoardPriceSpreadDtl, exchBoardPriceSpreadDetailDto)
                                exchBoardPriceSpreadDetailDto
                            }.toList()
                            BeanUtils.copyProperties(exchBoardPriceSpread, exchBoardPriceSpreadDto)
                            exchBoardMap[exchBoardPriceSpread.exchangeBoardOid]?.let { exchBoard -> exchBoardPriceSpreadDto.exchangeBoardCode = exchBoard.exchangeBoardCode }
                            exchBoardPriceSpreadDto
                        }
                    }
        }.flatMapIterable { it }
    }

    fun modelToDto(authenToken: AuthenticationToken, exchangeBoards: List<ExchangeBoardPriceSpread>): Flux<ExchangeBoardPriceSpreadDto> {
        return modelToDto(authenToken, Flux.fromIterable(exchangeBoards))
    }

    fun modelToDto(authenToken: AuthenticationToken, exchangeBoardPriceSpread: ExchangeBoardPriceSpread, exchangeBoard: ExchangeBoard): ExchangeBoardPriceSpreadDto {
        val exchBoardPriceSpreadDto = ExchangeBoardPriceSpreadDto()
        BeanUtils.copyProperties(exchangeBoardPriceSpread, exchBoardPriceSpreadDto)
        exchBoardPriceSpreadDto.exchangeBoardCode = exchangeBoard.exchangeBoardCode
        exchBoardPriceSpreadDto.exchangeBoardPriceSpreadDetail = exchangeBoardPriceSpread.exchangeBoardPriceSpreadDetail.map {
            val exchBoardPriceSpreadDetailDto = ExchangeBoardPriceSpreadDetailDto()
            BeanUtils.copyProperties(it, exchBoardPriceSpreadDetailDto)
            exchBoardPriceSpreadDetailDto
        }.toList()
        return exchBoardPriceSpreadDto
    }

    fun modelToDto(authenToken: AuthenticationToken, exchangeBoardPriceSpread: Mono<ExchangeBoardPriceSpread>, exchange: ExchangeBoard?): Mono<ExchangeBoardPriceSpreadDto> {
        return exchangeBoardPriceSpread.flatMap { exchBoardPriceSpread ->
            val exchBoardPriceSpreadDto = ExchangeBoardPriceSpreadDto()
            BeanUtils.copyProperties(exchBoardPriceSpread, exchBoardPriceSpreadDto)
            exchBoardPriceSpreadDto.exchangeBoardPriceSpreadDetail = exchBoardPriceSpread.exchangeBoardPriceSpreadDetail.map { exchBoardPriceSpreadDtl ->
                val exchBoardPriceSpreadDetailDto = ExchangeBoardPriceSpreadDetailDto()
                BeanUtils.copyProperties(exchBoardPriceSpreadDtl, exchBoardPriceSpreadDetailDto)
                exchBoardPriceSpreadDetailDto
            }.toList()
            exchangeBoardService.findByOid(authenToken, exchBoardPriceSpread.exchangeBoardOid).map { exchBoard ->
                exchBoardPriceSpreadDto.exchangeBoardCode = exchBoard.exchangeBoardCode
                exchBoardPriceSpreadDto
            }.defaultIfEmpty(exchBoardPriceSpreadDto)
        }
    }

//    fun modelToDto(authenToken: AuthenticationToken, exchangeBoardPriceSpread: ExchangeBoardPriceSpread, exchangeBoard: ExchangeBoard?): ExchangeBoardPriceSpreadDto {
//        return modelToDto(authenToken, Mono.just(exchangeBoardPriceSpread), exchangeBoard).block()!!
//    }

}