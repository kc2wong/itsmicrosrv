package com.exiasoft.itsorder.mapper

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itsorder.dto.ChargeCommissionDto
import com.exiasoft.itsorder.dto.OrderDto
import com.exiasoft.itsorder.dto.OrderExecutionDto
import com.exiasoft.itsorder.dto.SimpleOrderDto
import com.exiasoft.itsorder.model.ChargeCommission
import com.exiasoft.itsorder.model.Order
import com.exiasoft.itsorder.model.SimpleOrder
import com.exiasoft.itsstaticdata.model.Instrument
import com.exiasoft.itsstaticdata.service.InstrumentService
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class OrderMapper(val instrumentService: InstrumentService) {

    fun modelToDto(authenToken: AuthenticationToken, orderList: Flux<Order>): Flux<OrderDto> {
        val instrumentMap = mutableMapOf<String, Instrument>()
        return orderList.flatMap {order ->
            Mono.justOrEmpty(instrumentMap[order.instrumentOid]).switchIfEmpty(
                    instrumentService.findByOid(authenToken, order.instrumentOid)
            ).map {instrument ->
                val orderDto = OrderDto()
                BeanUtils.copyProperties(order, orderDto)
                orderDto.orderExecution = order.orderExecution.map {
                    val orderExecutionDto = OrderExecutionDto()
                    BeanUtils.copyProperties(it, orderExecutionDto)
                    orderExecutionDto
                }.toList()
                instrumentMap[order.instrumentOid] = instrument
                orderDto.instrumentCode = instrument.instrumentCode
                orderDto
            }
        }
    }

    fun modelToDto(authenToken: AuthenticationToken, orderList: List<Order>): Flux<OrderDto> {
        return modelToDto(authenToken, Flux.fromIterable(orderList))
    }

    fun modelToDto(authenToken: AuthenticationToken, order: Mono<Order>): Mono<OrderDto> {
        return modelToDto(authenToken, order.flatMapMany { Mono.just(it) }).last()
    }

    fun modelToDto(authenToken: AuthenticationToken, order: Order): Mono<OrderDto> {
        return modelToDto(authenToken, Mono.just(order))
    }

    fun simpleModelToDto(authenToken: AuthenticationToken, simpleOrderList: Flux<SimpleOrder>): Flux<SimpleOrderDto> {
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

    fun simpleModelToDto(authenToken: AuthenticationToken, simpleOrderList: List<SimpleOrder>): Flux<SimpleOrderDto> {
        return simpleModelToDto(authenToken, Flux.fromIterable(simpleOrderList))
    }

    fun simpleModelToDto(authenToken: AuthenticationToken, simpleOrder: Mono<SimpleOrder>): Mono<SimpleOrderDto> {
        return simpleModelToDto(authenToken, simpleOrder.flatMapMany { Mono.just(it) }).last()
    }

    fun simpleModelToDto(authenToken: AuthenticationToken, simpleOrder: SimpleOrder): Mono<SimpleOrderDto> {
        return simpleModelToDto(authenToken, Mono.just(simpleOrder))
    }

}