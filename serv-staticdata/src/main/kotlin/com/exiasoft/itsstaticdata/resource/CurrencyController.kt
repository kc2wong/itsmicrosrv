package com.exiasoft.itsstaticdata.resource

import com.exiasoft.itscommon.annotation.PageableDefault
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.model.PagingSearchResult
import com.exiasoft.itscommon.resource.BaseResource
import com.exiasoft.itscommon.util.WebResponseUtil
import com.exiasoft.itsstaticdata.CONTEXT_PATH
import com.exiasoft.itsstaticdata.CONTEXT_PATH_INTERNAL
import com.exiasoft.itsstaticdata.dto.CurrencyDto
import com.exiasoft.itsstaticdata.mapper.CurrencyMapper
import com.exiasoft.itsstaticdata.model.Currency
import com.exiasoft.itsstaticdata.service.CurrencyService
import mu.KotlinLogging
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping(CONTEXT_PATH)
class CurrencyController(
        val currencyService: CurrencyService,
        val currencyMapper: CurrencyMapper
): BaseResource() {

    private val logger = KotlinLogging.logger {}

    @PreAuthorize("@permissionEvaluator.hasPermission(authentication, '{StaticDataGrp.Currency.Maintenance,StaticDataGrp.LookupHelper}')")
    @GetMapping("/sapi/currencies")
    fun find(@RequestParam(name = "descptDefLang", required = false) descptDefLang: String?,
             @PageableDefault(sort = ["currencyCode,asc"]) pageable: Pageable,
            authenToken: AuthenticationToken,
            request: ServerHttpRequest
    ): Mono<ResponseEntity<PagingSearchResult<CurrencyDto>>> {
        logger.debug("REST request to get a page of Currency")
        val page = currencyService.find(authenToken, descptDefLang, pageable)
        logger.info("Currency page retrieved, result = {} ", page)
        return WebResponseUtil.generatePaginationResponseEntity(request.path.toString(), page) { v -> currencyMapper.modelToDto(authenToken, v) }
    }

    @PreAuthorize("@permissionEvaluator.hasPermission(authentication, '{StaticDataGrp.Currency.Maintenance,StaticDataGrp.LookupHelper}')")
    @GetMapping("/sapi/currencies/{currencyCode}")
    fun findOne(@PathVariable currencyCode: String,
                authenToken: AuthenticationToken,
                request: ServerHttpRequest
    ): Mono<ResponseEntity<CurrencyDto>> {
        logger.debug("REST request to get a currency, currencyCode = {}", currencyCode)
        val currency = currencyService.findByIdentifier(authenToken, currencyCode)
        logger.info("Currency retrieved, result = {} ", currency)
        return WebResponseUtil.wrapOrNotFound(currency.map { currencyMapper.modelToDto(authenToken, it) })
    }

}

@RestController
@RequestMapping(CONTEXT_PATH_INTERNAL)
class CurrencyIntController(
        val currencyService: CurrencyService
): BaseResource() {

    private val logger = KotlinLogging.logger {}

    @GetMapping("/sapi/currencies/{oid}")
    fun findByOid(@PathVariable oid: String,
                  authenToken: AuthenticationToken,
                  request: ServerHttpRequest): Mono<Currency> {
        return currencyService.findByOid(authenToken, oid)
    }

    @GetMapping("/sapi/currencies")
    fun find(@RequestParam(value = "descptDefLang", required = false) descptDefLang: String?,
             @PageableDefault(sort = ["currencyOid,asc"]) pageable: Pageable,
             authenToken: AuthenticationToken,
             request: ServerHttpRequest
    ): Mono<PagingSearchResult<Currency>> {
        return currencyService.find(authenToken, descptDefLang, pageable).map {
            PagingSearchResult(it, it.content)
        }
    }

    @PostMapping("/sapi/currencies")
    fun findByOids(@RequestBody oids: Set<String>,
                   @PageableDefault(sort = ["currencyOid,asc"]) pageable: Pageable,
                   authenToken: AuthenticationToken,
                   request: ServerHttpRequest
    ): Mono<Map<String, Currency>> {
        return currencyService.findByOids(authenToken, oids)
    }

}