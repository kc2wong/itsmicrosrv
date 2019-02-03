package com.exiasoft.itsaccount.mapper

import com.exiasoft.itsaccount.dto.SimpleTradingAccountDto
import com.exiasoft.itsaccount.model.SimpleTradingAccount
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itsstaticdata.model.SimpleOperationUnit
import com.exiasoft.itsstaticdata.model.TradingAccountType
import com.exiasoft.itsstaticdata.service.SimpleOperationUnitService
import com.exiasoft.itsstaticdata.service.TradingAccountTypeService
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class TradingAccountMapper(val tradingAccountTypeService: TradingAccountTypeService, val simpleOperationUnitService: SimpleOperationUnitService) {

    fun modelToDto(authenToken: AuthenticationToken, simpleTradingAccounts: Flux<SimpleTradingAccount>): Flux<SimpleTradingAccountDto> {
        val tradingAccountTypeMap = mutableMapOf<String, TradingAccountType>()
        val operationUnitMap = mutableMapOf<String, SimpleOperationUnit>()

        return simpleTradingAccounts.flatMap { simpleTradingAccount ->
            val tradingAccountTypeResult = Mono.justOrEmpty(tradingAccountTypeMap[simpleTradingAccount.tradingAccountTypeOid])
                    .switchIfEmpty(tradingAccountTypeService.findByOid(authenToken, simpleTradingAccount.tradingAccountTypeOid))
            val operationUnitResult = Mono.justOrEmpty(operationUnitMap[simpleTradingAccount.operationUnitOid])
                    .switchIfEmpty(simpleOperationUnitService.findByOid(authenToken, simpleTradingAccount.operationUnitOid))

            tradingAccountTypeResult.zipWith(operationUnitResult).map {
                val simpleTradingAccountDto = SimpleTradingAccountDto()

                // store result in local map may not help a lot, may consider get all records first as below commented code
                tradingAccountTypeMap[simpleTradingAccount.tradingAccountTypeOid] = it.t1
                operationUnitMap[simpleTradingAccount.operationUnitOid] = it.t2

                BeanUtils.copyProperties(simpleTradingAccount, simpleTradingAccountDto)
                simpleTradingAccountDto.tradingAccountTypeCode = it.t1.tradingAccountTypeCode
                simpleTradingAccountDto.operationUnitCode = it.t2.operationUnitCode
                simpleTradingAccountDto
            }
        }
/*
        return tradingAccountTypeService.findAll(authenToken, PageUtil.unlimit()).flatMapMany { tradingAccountTypeResult ->
            val tradingAccountTypeMap = tradingAccountTypeResult.content.map { it.tradingAccountTypeOid to it.tradingAccountTypeCode }.toMap()
            simpleOperationUnitService.findAll(authenToken, PageUtil.unlimit()).flatMapMany { operationUnitResult ->
                val operationUnitMap = operationUnitResult.content.map { it.operationUnitOid to it.operationUnitCode }.toMap()
                simpleTradingAccounts.map { simpleTradingAccount ->
                    val simpleTradingAccountDto = SimpleTradingAccountDto()
                    BeanUtils.copyProperties(simpleTradingAccount, simpleTradingAccountDto)
                    tradingAccountTypeMap[simpleTradingAccount.tradingAccountTypeOid] ?.let { simpleTradingAccountDto.tradingAccountTypeCode = it}
                    operationUnitMap[simpleTradingAccount.operationUnitOid] ?.let { simpleTradingAccountDto.operationUnitCode = it}
                    simpleTradingAccountDto
                }
            }
        }
*/
    }

    fun modelToDto(authenToken: AuthenticationToken, simpleTradingAccounts: List<SimpleTradingAccount>): Flux<SimpleTradingAccountDto> {
        return modelToDto(authenToken, Flux.fromIterable(simpleTradingAccounts))
    }

    fun modelToDto(authenToken: AuthenticationToken, simpleTradingAccount: Mono<SimpleTradingAccount>): Mono<SimpleTradingAccountDto> {
        return modelToDto(authenToken, simpleTradingAccount.flatMapMany { Mono.just(it) }).last()
    }

}