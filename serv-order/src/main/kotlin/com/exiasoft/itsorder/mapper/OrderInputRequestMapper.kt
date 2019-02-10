package com.exiasoft.itsorder.mapper

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itsorder.dto.OrderInputRequestDto
import com.exiasoft.itsorder.model.OrderInputRequest
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class OrderInputRequestMapper() {

    fun dtoToModel(authenToken: AuthenticationToken, orderInputRequestDto: OrderInputRequestDto): Mono<OrderInputRequest> {
//        val orderInputRequest = OrderInputRequest()
//        BeanUtils.copyProperties(orderInputRequestDto, orderInputRequest)
//        return orderInputRequest;
        return dtoToModel(authenToken, Mono.just(orderInputRequestDto))
    }

    fun dtoToModel(authenToken: AuthenticationToken, orderInputRequestDto: Mono<OrderInputRequestDto>): Mono<OrderInputRequest> {
        return orderInputRequestDto.map {
            val orderInputRequest = OrderInputRequest()
            BeanUtils.copyProperties(it, orderInputRequest)
            orderInputRequest
        }
    }

}
