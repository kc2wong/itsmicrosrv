package com.exiasoft.itsstaticdata.mapper

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itsstaticdata.dto.OrderTypeDto
import com.exiasoft.itsstaticdata.model.OrderType
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class OrderTypeMapper {

    fun modelToDto(authenToken: AuthenticationToken, currencies: Flux<OrderType>): Flux<OrderTypeDto> {
        return currencies.map {
            modelToDto(authenToken, it)
        }
    }

    fun modelToDto(authenToken: AuthenticationToken, currencies: List<OrderType>): Flux<OrderTypeDto> {
        return Flux.fromIterable(currencies.map {
            modelToDto(authenToken, it)
        })
    }

    fun modelToDto(authenToken: AuthenticationToken, currency: Mono<OrderType>): Mono<OrderTypeDto> {
        return currency.map {
            modelToDto(authenToken, it)
        }
    }

    fun modelToDto(authenToken: AuthenticationToken, currency: OrderType): OrderTypeDto {
        val orderTypeDto = OrderTypeDto()
        BeanUtils.copyProperties(currency, orderTypeDto)
        return orderTypeDto
    }

}