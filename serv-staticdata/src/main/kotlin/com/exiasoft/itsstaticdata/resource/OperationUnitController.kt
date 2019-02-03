package com.exiasoft.itsstaticdata.resource

import com.exiasoft.itscommon.annotation.PageableDefault
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.model.PagingSearchResult
import com.exiasoft.itscommon.resource.BaseResource
import com.exiasoft.itsstaticdata.CONTEXT_PATH_INTERNAL
import com.exiasoft.itsstaticdata.model.SimpleOperationUnit
import com.exiasoft.itsstaticdata.service.SimpleOperationUnitService
import mu.KotlinLogging
import org.springframework.data.domain.Pageable
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping(CONTEXT_PATH_INTERNAL)
class OperationUnitIntController (
        val operationUnitService: SimpleOperationUnitService
) : BaseResource() {

    private val logger = KotlinLogging.logger {}

    @GetMapping("/sapi/operation-units/{oid}")
    fun findByOid(@PathVariable oid: String,
                  authenToken: AuthenticationToken,
                  request: ServerHttpRequest): Mono<SimpleOperationUnit> {
        return operationUnitService.findByOid(authenToken, oid)
    }

    @GetMapping("/sapi/operation-units")
    fun find(@RequestParam(value = "nameOneDefLang", required = false) nameOneDefLang: String?,
             @PageableDefault(sort = ["operationUnitOid,asc"]) pageable: Pageable,
             authenToken: AuthenticationToken,
             request: ServerHttpRequest
    ): Mono<PagingSearchResult<SimpleOperationUnit>> {
        return operationUnitService.find(authenToken, nameOneDefLang, pageable).map {
            PagingSearchResult(it, it.content)
        }
    }

    @PostMapping("/sapi/operation-units")
    fun findByOids(@RequestBody oids: Set<String>,
                   @PageableDefault(sort = ["operationUnitOid,asc"]) pageable: Pageable,
                   authenToken: AuthenticationToken,
                   request: ServerHttpRequest
    ): Mono<Map<String, SimpleOperationUnit>> {
        return operationUnitService.findByOids(authenToken, oids)
    }

}