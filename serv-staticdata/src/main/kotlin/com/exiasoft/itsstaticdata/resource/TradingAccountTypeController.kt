package com.exiasoft.itsstaticdata.resource

import com.exiasoft.itscommon.annotation.PageableDefault
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.model.PagingSearchResult
import com.exiasoft.itscommon.resource.BaseResource
import com.exiasoft.itsstaticdata.CONTEXT_PATH_INTERNAL
import com.exiasoft.itsstaticdata.model.TradingAccountType
import com.exiasoft.itsstaticdata.service.TradingAccountTypeService
import mu.KotlinLogging
import org.springframework.data.domain.Pageable
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping(CONTEXT_PATH_INTERNAL)
class TradingAccountTypeIntController (
        val tradingAccountTypeService: TradingAccountTypeService
) : BaseResource() {

    private val logger = KotlinLogging.logger {}

    @GetMapping("/sapi/trading-account-types/{oid}")
    fun findByOid(@PathVariable oid: String,
                  authenToken: AuthenticationToken,
                  request: ServerHttpRequest): Mono<TradingAccountType> {
        logger.info ( "TradingAccountTypeIntController.findByOid() - starts" )
        return tradingAccountTypeService.findByOid(authenToken, oid)
    }

    @GetMapping("/sapi/trading-account-types")
    fun find(@RequestParam(value = "description", required = false) description: String?,
             @PageableDefault(sort = ["tradingAccountTypeOid,asc"]) pageable: Pageable,
             authenToken: AuthenticationToken,
             request: ServerHttpRequest
    ): Mono<PagingSearchResult<TradingAccountType>> {
        return tradingAccountTypeService.find(authenToken, description, pageable).map {
            PagingSearchResult(it, it.content)
        }
    }

    @PostMapping("/sapi/trading-account-types")
    fun findByOids(@RequestBody oids: Set<String>,
                   @PageableDefault(sort = ["tradingAccountTypeOid,asc"]) pageable: Pageable,
                   authenToken: AuthenticationToken,
                   request: ServerHttpRequest
    ): Mono<Map<String, TradingAccountType>> {
        return tradingAccountTypeService.findByOids(authenToken, oids)
    }

}