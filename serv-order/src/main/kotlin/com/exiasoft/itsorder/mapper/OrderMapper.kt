package com.exiasoft.itsorder.mapper

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itsorder.dto.SimpleOrderDto
import com.exiasoft.itsorder.model.SimpleOrder
import com.exiasoft.itsstaticdata.model.Instrument
import com.exiasoft.itsstaticdata.service.InstrumentService
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class OrderMapper(val instrumentService: InstrumentService) {

    fun modelToDto(authenToken: AuthenticationToken, simpleOrderList: Flux<SimpleOrder>): Flux<SimpleOrderDto> {
        val instrumentMap = mutableMapOf<String, Instrument>()
        return simpleOrderList.flatMap {simpleOrder ->
            Mono.justOrEmpty(instrumentMap[simpleOrder.instrumentOid]).switchIfEmpty(
                    instrumentService.findByOid(authenToken, simpleOrder.instrumentOid)
            ).map {instrument ->
                val simpleOrderDto = SimpleOrderDto()
                BeanUtils.copyProperties(simpleOrder, simpleOrderDto)
                instrumentMap[simpleOrder.instrumentOid] = instrument
                simpleOrderDto.instrumentCode = instrument.instrumentCode
                simpleOrderDto
            }
        }
    }

    fun modelToDto(authenToken: AuthenticationToken, simpleOrderList: List<SimpleOrder>): Flux<SimpleOrderDto> {
        return modelToDto(authenToken, Flux.fromIterable(simpleOrderList))
    }

    fun modelToDto(authenToken: AuthenticationToken, simpleOrder: Mono<SimpleOrder>): Mono<SimpleOrderDto> {
        return modelToDto(authenToken, simpleOrder.flatMapMany { Mono.just(it) }).last()
    }

}