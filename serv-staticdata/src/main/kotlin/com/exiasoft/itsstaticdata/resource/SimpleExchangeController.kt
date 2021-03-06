package com.exiasoft.itsstaticdata.resource

import com.exiasoft.itscommon.annotation.PageableDefault
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.model.PagingSearchResult
import com.exiasoft.itscommon.resource.BaseResource
import com.exiasoft.itscommon.util.WebResponseUtil
import com.exiasoft.itsstaticdata.CONTEXT_PATH
import com.exiasoft.itsstaticdata.CONTEXT_PATH_INTERNAL
import com.exiasoft.itsstaticdata.dto.ExchangeDto
import com.exiasoft.itsstaticdata.dto.SimpleExchangeDto
import com.exiasoft.itsstaticdata.mapper.ExchangeMapper
import com.exiasoft.itsstaticdata.model.Exchange
import com.exiasoft.itsstaticdata.model.SimpleExchange
import com.exiasoft.itsstaticdata.service.ExchangeService
import com.exiasoft.itsstaticdata.service.SimpleExchangeService
import mu.KotlinLogging
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping(CONTEXT_PATH)
class SimpleExchangeController(
        val exchangeService: SimpleExchangeService,
        val exchangeMapper: ExchangeMapper
): BaseResource() {

    private val logger = KotlinLogging.logger {}

    @PreAuthorize("@permissionEvaluator.hasPermission(authentication, '{StaticDataGrp.Exchange.Maintenance,StaticDataGrp.LookupHelper}')")
    @GetMapping("/sapi/simple-exchanges")
    fun find(@RequestParam("nameDefLang", required = false) nameDefLang: String?,
             @PageableDefault(sort = ["exchangeCode,asc"]) pageable: Pageable,
             authenToken: AuthenticationToken,
             request: ServerHttpRequest
    ): Mono<ResponseEntity<PagingSearchResult<SimpleExchangeDto>>> {
        logger.debug("REST request to get a page of Exchanges")
        val page = exchangeService.find(authenToken, nameDefLang, pageable)
        logger.info("Exchange page retrieved, result = {} ", page)

        return WebResponseUtil.generatePaginationResponseEntity(request.path.toString(), page) { exchangeMapper.simpleModelToDto(authenToken, it) }
    }

    @PreAuthorize("@permissionEvaluator.hasPermission(authentication, '{StaticDataGrp.Exchange.Maintenance,StaticDataGrp.LookupHelper}')")
    @GetMapping("/sapi/simple-exchanges/{exchangeCode}")
    fun findOne(@PathVariable exchangeCode: String,
                authenToken: AuthenticationToken,
                httpServletRequest: ServerHttpRequest
    ): Mono<ResponseEntity<SimpleExchangeDto>> {
        logger.debug("REST request to get a exchange, exchangeCode = {}", exchangeCode)
        val exchange = exchangeService.findByIdentifier(authenToken, exchangeCode)
        logger.info("Exchange retrieved, result = {} ", exchange)
        return WebResponseUtil.wrapOrNotFound(exchangeMapper.simpleModelToDto(authenToken, exchange))
    }

}


@RestController
@RequestMapping(CONTEXT_PATH_INTERNAL)
class SimpleExchangeIntController(
        val exchangeService: SimpleExchangeService
): BaseResource() {

    private val logger = KotlinLogging.logger {}

    @GetMapping("/sapi/simple-exchanges/{oid}")
    fun findByOid(@PathVariable oid: String,
                  authenToken: AuthenticationToken,
                  request: ServerHttpRequest
    ): Mono<SimpleExchange> {
        return exchangeService.findByOid(authenToken, oid)
    }

    @GetMapping("/sapi/simple-exchanges")
    fun find(@RequestParam(value = "nameDefLang", required = false) nameDefLang: String?,
             @PageableDefault(sort = ["exchangeOid,asc"]) pageable: Pageable,
             authenToken: AuthenticationToken,
             request: ServerHttpRequest
    ): Mono<PagingSearchResult<SimpleExchange>> {
        return exchangeService.find(authenToken, nameDefLang, pageable).map {
            WebResponseUtil.generatePaginationResponse(it, it.content)
        }
    }

    @PostMapping("/sapi/simple-exchanges")
    fun findByOids(@RequestBody oids: Set<String>,
                   @PageableDefault(sort = ["exchangeOid,asc"]) pageable: Pageable,
                   authenToken: AuthenticationToken,
                   request: ServerHttpRequest
    ): Mono<Map<String, SimpleExchange>> {
        return exchangeService.findByOids(authenToken, oids)
    }

}