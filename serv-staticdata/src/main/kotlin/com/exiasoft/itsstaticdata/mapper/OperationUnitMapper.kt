package com.exiasoft.itsstaticdata.mapper

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itsstaticdata.dto.SimpleOperationUnitDto
import com.exiasoft.itsstaticdata.model.SimpleOperationUnit
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class OperationUnitMapper {

    fun modelToDto(authenToken: AuthenticationToken, operationUnits: Flux<SimpleOperationUnit>): Flux<SimpleOperationUnitDto> {
        return operationUnits.map {
            modelToDto(authenToken, it)
        }
    }

    fun modelToDto(authenToken: AuthenticationToken, operationUnits: List<SimpleOperationUnit>): Flux<SimpleOperationUnitDto> {
        return Flux.fromIterable(operationUnits.map {
            modelToDto(authenToken, it)
        })
    }

    fun modelToDto(authenToken: AuthenticationToken, operationUnit: Mono<SimpleOperationUnit>): Mono<SimpleOperationUnitDto> {
        return operationUnit.map {
            modelToDto(authenToken, it)
        }
    }

    fun modelToDto(authenToken: AuthenticationToken, operationUnit: SimpleOperationUnit): SimpleOperationUnitDto {
        val simpleOperationUnitDto = SimpleOperationUnitDto()
        BeanUtils.copyProperties(operationUnit, simpleOperationUnitDto)
        return simpleOperationUnitDto
    }

}