package com.exiasoft.itsstaticdata.mapper

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itsstaticdata.dto.ExchangeBoardDto
import com.exiasoft.itsstaticdata.model.ExchangeBoard
import com.exiasoft.itsstaticdata.model.SimpleExchange
import com.exiasoft.itsstaticdata.service.ExchangeService
import mu.KotlinLogging
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
//class ExchangeMapper(val operationUnitService: CurrencyService, val exchangeParameterMapper: ExchangeParameterMapper, val exchangeOrderTypeMapper: ExchangeOrderTypeMapper) {
class ExchangeBoardMapper(val exchangeService: ExchangeService) {

    private val logger = KotlinLogging.logger {}

    fun modelToDto(authenToken: AuthenticationToken, exchangeBoards: Flux<ExchangeBoard>): Flux<ExchangeBoardDto> {
        return exchangeBoards.collectList().flatMap { exchBoardList ->
            exchangeService.findByOids(authenToken, exchBoardList.asSequence().map { exchBoard -> exchBoard.exchangeOid }.toSet())
                    .defaultIfEmpty(emptyMap()).map { exchMap ->
                        exchBoardList.map { exchBoard ->
                            val exchangeBoardDto = ExchangeBoardDto()
                            BeanUtils.copyProperties(exchBoard, exchangeBoardDto)
                            exchMap[exchBoard.exchangeOid]?.let { exch -> exchangeBoardDto.exchangeCode = exch.exchangeCode }
                            exchangeBoardDto
                        }
                    }
        }.flatMapIterable { it }
    }

    fun modelToDto(authenToken: AuthenticationToken, exchangeBoards: List<ExchangeBoard>): Flux<ExchangeBoardDto> {
        return modelToDto(authenToken, Flux.fromIterable(exchangeBoards))
    }

    fun modelToDto(authenToken: AuthenticationToken, exchangeBoard: Mono<ExchangeBoard>, exchange: SimpleExchange?): Mono<ExchangeBoardDto> {
        return exchangeBoard.flatMap { exchBoard ->
            val exchangeBoardDto = ExchangeBoardDto()
            BeanUtils.copyProperties(exchBoard, exchangeBoardDto)
            exchangeService.findByOid(authenToken, exchBoard.exchangeOid).map { exch ->
                exchangeBoardDto.exchangeCode = exch.exchangeCode
                exchangeBoardDto
            }.defaultIfEmpty(exchangeBoardDto)
        }
    }

    fun modelToDto(authenToken: AuthenticationToken, exchangeBoard: ExchangeBoard, exchange: SimpleExchange?): ExchangeBoardDto {
        return modelToDto(authenToken, Mono.just(exchangeBoard), exchange).block()!!
    }

}