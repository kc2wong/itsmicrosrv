package com.exiasoft.itsorder.mapper

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itsorder.dto.ChargeCommissionDto
import com.exiasoft.itsorder.model.ChargeCommission
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ChargeCommissionMapper {

    fun modelToDto(authenToken: AuthenticationToken, chargeCommission: Mono<ChargeCommission>): Mono<ChargeCommissionDto> {
        return chargeCommission.map {
            val chargeCommissionDto = ChargeCommissionDto()
            BeanUtils.copyProperties(it, chargeCommissionDto)
            chargeCommissionDto
        }
    }

    fun modelToDto(authenToken: AuthenticationToken, chargeCommission: ChargeCommission): Mono<ChargeCommissionDto> {
        return modelToDto(authenToken, Mono.just(chargeCommission))
    }

}