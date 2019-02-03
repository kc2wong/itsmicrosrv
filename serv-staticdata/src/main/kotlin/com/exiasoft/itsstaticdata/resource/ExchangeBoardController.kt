package com.exiasoft.itsstaticdata.resource

import com.exiasoft.itscommon.annotation.PageableDefault
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.model.PagingSearchResult
import com.exiasoft.itscommon.resource.BaseResource
import com.exiasoft.itscommon.util.WebResponseUtil
import com.exiasoft.itsstaticdata.CONTEXT_PATH
import com.exiasoft.itsstaticdata.CONTEXT_PATH_INTERNAL
import com.exiasoft.itsstaticdata.dto.ExchangeBoardDto
import com.exiasoft.itsstaticdata.mapper.ExchangeBoardMapper
import com.exiasoft.itsstaticdata.model.ExchangeBoard
import com.exiasoft.itsstaticdata.service.ExchangeBoardService
import com.exiasoft.itsstaticdata.service.ExchangeService
import mu.KotlinLogging
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping(CONTEXT_PATH)
class ExchangeBoardController(
        val exchangeService: ExchangeService,
        val exchangeBoardService: ExchangeBoardService,
        val exchangeBoardMapper: ExchangeBoardMapper
): BaseResource() {

    private val logger = KotlinLogging.logger {}

    @GetMapping("/sapi/exchange-boards")
    fun find(@RequestParam("exchangeCode", required = false) exchangeCode: String?,
             @RequestParam("exchangeBoardCode", required = false) exchangeBoardCode: String?,
             @RequestParam("nameDefLang", required = false) nameDefLang: String?,
             @PageableDefault(sort = ["exchangeBoardCode,asc"]) pageable: Pageable,
             authenToken: AuthenticationToken,
             request: ServerHttpRequest
    ): Mono<ResponseEntity<PagingSearchResult<ExchangeBoardDto>>> {
        logger.debug("REST request to get a page of ExchangeBoards")
        val page = if (exchangeCode != null) exchangeService.findByIdentifier(authenToken, exchangeCode).flatMap { exch ->
            exchangeBoardService.find(authenToken, exch.exchangeOid, exchangeBoardCode, nameDefLang, pageable)
        }.switchIfEmpty(Mono.just(PageImpl(emptyList(), pageable, 0))) else  {
            exchangeBoardService.find(authenToken, null, exchangeBoardCode, nameDefLang, pageable)
        }
        logger.info("ExchangeBoard page retrieved, result = {} ", page)
        return WebResponseUtil.generatePaginationResponseEntity(request.path.toString(), page) { exchangeBoardMapper.modelToDto(authenToken, it) }
    }

    @GetMapping("/sapi/exchanges/{exchangeCode}/exchange-boards/{exchangeBoardCode}")
    fun findOne(@PathVariable exchangeCode: String,
                @PathVariable exchangeBoardCode: String,
                authenToken: AuthenticationToken,
                httpRequest: ServerHttpRequest
    ): Mono<ResponseEntity<ExchangeBoardDto>> {
        logger.debug("REST request to get a ExchangeBoard, exchangeCode = {}, exchangeBoardCode = {}", exchangeCode, exchangeBoardCode)
        val exchangeBoard = exchangeService.findByIdentifier(authenToken, exchangeCode).flatMap { exch ->
            exchangeBoardService.findByIdentifier(authenToken, ExchangeBoard.Id(exch.exchangeOid, exchangeBoardCode))
        }
        logger.info("ExchangeBoard retrieved, result = {} ", exchangeBoard)
        return WebResponseUtil.wrapOrNotFound(exchangeBoardMapper.modelToDto(authenToken, exchangeBoard, null))
    }

}


@RestController
@RequestMapping(CONTEXT_PATH_INTERNAL)
class ExchangeBoardIntController(
        val exchangeBoardService: ExchangeBoardService
): BaseResource() {

    private val logger = KotlinLogging.logger {}

    @GetMapping("/sapi/exchange-boards/{oid}")
    fun findByOid(@PathVariable oid: String,
                  authenToken: AuthenticationToken,
                  request: ServerHttpRequest): Mono<ExchangeBoard> {
        return exchangeBoardService.findByOid(authenToken, oid)
    }

    @GetMapping("/sapi/exchange-boards")
    fun find(@RequestParam(value = "exchangeOid", required = false) exchangeOid: String?,
             @RequestParam(value = "exchangeBoardCode", required = false) exchangeBoardCode: String?,
             @RequestParam(value = "nameDefLang", required = false) nameDefLang: String?,
             @PageableDefault(sort = ["exchangeBoardOid,asc"]) pageable: Pageable,
             authenToken: AuthenticationToken,
             request: ServerHttpRequest
    ): Mono<PagingSearchResult<ExchangeBoard>> {
        return exchangeBoardService.find(authenToken, exchangeOid, exchangeBoardCode, nameDefLang, pageable).map {
            PagingSearchResult(it, it.content)
        }
    }

    @PostMapping("/sapi/exchange-boards")
    fun findByOids(@RequestBody oids: Set<String>,
                   @PageableDefault(sort = ["exchangeBoardOid,asc"]) pageable: Pageable,
                   authenToken: AuthenticationToken,
                   request: ServerHttpRequest
    ): Mono<Map<String, ExchangeBoard>> {
        return exchangeBoardService.findByOids(authenToken, oids)
    }

}