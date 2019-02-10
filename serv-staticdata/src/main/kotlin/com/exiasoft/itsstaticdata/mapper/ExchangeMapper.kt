package com.exiasoft.itsstaticdata.mapper

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.util.PageUtil
import com.exiasoft.itsstaticdata.dto.ExchangeDto
import com.exiasoft.itsstaticdata.dto.ExchangeOrderTypeDto
import com.exiasoft.itsstaticdata.dto.ExchangeParameterDto
import com.exiasoft.itsstaticdata.dto.SimpleExchangeDto
import com.exiasoft.itsstaticdata.model.Exchange
import com.exiasoft.itsstaticdata.model.SimpleExchange
import com.exiasoft.itsstaticdata.service.CurrencyService
import com.exiasoft.itsstaticdata.service.OrderChannelService
import com.exiasoft.itsstaticdata.service.OrderTypeService
import mu.KotlinLogging
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ExchangeMapper(val currencyService: CurrencyService, val orderChannelService: OrderChannelService, val orderTypeService: OrderTypeService) {

    private val logger = KotlinLogging.logger {}

    fun modelToDto(authenToken: AuthenticationToken, exchanges: Flux<Exchange>): Flux<ExchangeDto> {
        return currencyService.findAll(authenToken, Pageable.unpaged()).zipWith(
                orderChannelService.findAll(authenToken, Pageable.unpaged()).zipWith(
                        orderTypeService.findAll(authenToken, Pageable.unpaged())
                )
        ).flatMapMany {
            val currencyMap = it.t1.content.map { v -> v.currencyOid to v }.toMap()
            val orderChannelMap = it.t2.t1.content.map { v -> v.orderChannelOid to v }.toMap()
            val orderTypeMap = it.t2.t2.content.map { v -> v.orderTypeOid to v }.toMap()
            exchanges.map { exch ->
                val exchangeDto = ExchangeDto()
                BeanUtils.copyProperties(exch, exchangeDto)
                exchangeDto.exchangeOrderType = exch.exchangeOrderType.map { exchOrderType ->
                    val exchangeOrderTypeDto = ExchangeOrderTypeDto()
                    BeanUtils.copyProperties(exchOrderType, exchangeOrderTypeDto)
                    orderChannelMap[exchOrderType.orderChannelOid]?.let { exchangeOrderTypeDto.orderChannelCode = it.orderChannelCode }
                    orderTypeMap[exchOrderType.orderTypeOid]?.let { exchangeOrderTypeDto.orderTypeCode = it.orderTypeCode }
                    exchangeOrderTypeDto
                }
                exchangeDto.exchangeParameter = exch.exchangeParameter.map { exchPara ->
                    val esxchangeParameterDto = ExchangeParameterDto()
                    BeanUtils.copyProperties(exchPara, esxchangeParameterDto)
                    esxchangeParameterDto
                }
                currencyMap[exch.baseCurrencyOid]?.let { exchangeDto.baseCurrencyCode = it.currencyCode }
                exchangeDto
            }
        }
    }

    fun modelToDto(authenToken: AuthenticationToken, exchanges: List<Exchange>): Flux<ExchangeDto> {
        return modelToDto(authenToken, Flux.fromIterable(exchanges))
    }

    fun modelToDto(authenToken: AuthenticationToken, exchange: Mono<Exchange>): Mono<ExchangeDto> {
        return modelToDto(authenToken, exchange.flatMapMany { Mono.just(it) }).last()
    }

    fun simpleModelToDto(authenToken: AuthenticationToken, exchanges: Flux<SimpleExchange>): Flux<SimpleExchangeDto> {
        return currencyService.findAll(authenToken, PageUtil.unlimit()).flatMapMany { currencyList ->
            val currencyMap = currencyList.content.map { v -> v.currencyOid to v.currencyCode }.toMap()
            exchanges.map { exch ->
                val exchangeDto = SimpleExchangeDto()
                BeanUtils.copyProperties(exch, exchangeDto)
                currencyMap[exch.baseCurrencyOid] ?.let { exchangeDto.baseCurrencyCode = it }
                exchangeDto
            }
        }
    }

    fun simpleModelToDto(authenToken: AuthenticationToken, exchanges: List<SimpleExchange>): Flux<SimpleExchangeDto> {
        return simpleModelToDto(authenToken, Flux.fromIterable(exchanges))
    }

    fun simpleModelToDto(authenToken: AuthenticationToken, exchange: Mono<SimpleExchange>): Mono<SimpleExchangeDto> {
        return simpleModelToDto(authenToken, exchange.flatMapMany { Mono.just(it) }).last()
    }

    fun simpleModelToDto(authenToken: AuthenticationToken, exchange: SimpleExchange): SimpleExchangeDto {
        return simpleModelToDto(authenToken, Mono.just(exchange)).block()!!
    }

}