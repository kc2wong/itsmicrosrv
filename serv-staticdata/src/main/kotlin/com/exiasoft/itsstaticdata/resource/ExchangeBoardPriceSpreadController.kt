package com.exiasoft.itsstaticdata.resource

import com.exiasoft.itscommon.annotation.PageableDefault
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.model.PagingSearchResult
import com.exiasoft.itscommon.resource.BaseResource
import com.exiasoft.itscommon.util.WebResponseUtil
import com.exiasoft.itsstaticdata.CONTEXT_PATH
import com.exiasoft.itsstaticdata.CONTEXT_PATH_INTERNAL
import com.exiasoft.itsstaticdata.dto.ExchangeBoardPriceSpreadDto
import com.exiasoft.itsstaticdata.mapper.ExchangeBoardPriceSpreadMapper
import com.exiasoft.itsstaticdata.model.ExchangeBoard
import com.exiasoft.itsstaticdata.model.ExchangeBoardPriceSpread
import com.exiasoft.itsstaticdata.service.ExchangeBoardPriceSpreadService
import com.exiasoft.itsstaticdata.service.ExchangeBoardService
import com.exiasoft.itsstaticdata.service.ExchangeService
import mu.KotlinLogging
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping(CONTEXT_PATH)
class ExchangeBoardPriceSpreadController(
        val exchangeService: ExchangeService,
        val exchangeBoardService: ExchangeBoardService,
        val exchangeBoardPriceSpreadService: ExchangeBoardPriceSpreadService,
        val exchangeBoardPriceSpreadMapper: ExchangeBoardPriceSpreadMapper
): BaseResource() {

    private val logger = KotlinLogging.logger {}


    @GetMapping("/sapi/exchanges/{exchangeCode}/exchange-boards/{exchangeBoardCode}/price-spreads/{priceSpreadCode}")
    fun findOne(@PathVariable exchangeCode: String,
                @PathVariable exchangeBoardCode: String,
                @PathVariable priceSpreadCode: String,
                authenToken: AuthenticationToken,
                request: ServerHttpRequest
    ): Mono<ResponseEntity<ExchangeBoardPriceSpreadDto>> {
        logger.debug("REST request to get a exchangeBoardPriceSpread, exchangeCode = {}, exchangeBoardCode = {}, priceSpreadCode = {}", exchangeCode, exchangeBoardCode, priceSpreadCode)

        val exchBoardPriceSpread = exchangeService.findByIdentifier(authenToken, exchangeCode).flatMap { exch ->
            exchangeBoardService.findByIdentifier(authenToken, ExchangeBoard.Id(exch.exchangeOid, exchangeBoardCode)).flatMap { exchBoard ->
                exchangeBoardPriceSpreadService.findByIdentifier(authenToken, ExchangeBoardPriceSpread.Id(exchBoard.exchangeBoardOid, priceSpreadCode)).flatMap {
                    Mono.just(exchangeBoardPriceSpreadMapper.modelToDto(authenToken,it, exchBoard))
                }
            }
        }
        logger.info("ExchangeBoardPriceSpread retrieved, result = {} ", exchBoardPriceSpread)
        return WebResponseUtil.wrapOrNotFound(exchBoardPriceSpread)
    }
}


@RestController
@RequestMapping(CONTEXT_PATH_INTERNAL)
class ExchangeBoardPriceSpreadIntController(
        val exchangeBoardPriceSpreadService: ExchangeBoardPriceSpreadService
): BaseResource() {

    private val logger = KotlinLogging.logger {}

    @GetMapping("/sapi/price-spreads/{oid}")
    fun findByOid(@PathVariable oid: String,
                  authenToken: AuthenticationToken,
                  request: ServerHttpRequest
    ): Mono<ExchangeBoardPriceSpread> {
        return exchangeBoardPriceSpreadService.findByOid(authenToken, oid)
    }

    @GetMapping("/sapi/price-spreads")
    fun find(@RequestParam(value = "exchangeBoardOid", required = false) exchangeBoardOid: String?,
             @RequestParam(value = "exchangeBoardPriceSpreadCode", required = false) exchangeBoardPriceSpreadCode: String?,
             @PageableDefault(sort = ["exchangeBoardPriceSpreadOid,asc"]) pageable: Pageable,
             authenToken: AuthenticationToken,
             request: ServerHttpRequest
    ): Mono<PagingSearchResult<ExchangeBoardPriceSpread>> {
        return exchangeBoardPriceSpreadService.find(authenToken, exchangeBoardOid, exchangeBoardPriceSpreadCode, pageable).map {
            WebResponseUtil.generatePaginationResponse(it, it.content)
        }
    }

    @PostMapping("/sapi/price-spreads")
    fun findByOids(@RequestBody oids: Set<String>,
                   @PageableDefault(sort = ["exchangeBoardPriceSpreadOid,asc"]) pageable: Pageable,
                   authenToken: AuthenticationToken,
                   request: ServerHttpRequest
    ): Mono<Map<String, ExchangeBoardPriceSpread>> {
        return exchangeBoardPriceSpreadService.findByOids(authenToken, oids)
    }

}