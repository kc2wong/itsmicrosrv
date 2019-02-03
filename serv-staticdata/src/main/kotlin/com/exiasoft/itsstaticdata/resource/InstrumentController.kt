package com.exiasoft.itsstaticdata.resource

import com.exiasoft.itscommon.annotation.PageableDefault
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.model.PagingSearchResult
import com.exiasoft.itscommon.model.enumaration.Status
import com.exiasoft.itscommon.resource.BaseResource
import com.exiasoft.itscommon.util.EnumUtil
import com.exiasoft.itscommon.util.PageUtil
import com.exiasoft.itscommon.util.WebResponseUtil
import com.exiasoft.itsstaticdata.CONTEXT_PATH
import com.exiasoft.itsstaticdata.CONTEXT_PATH_INTERNAL
import com.exiasoft.itsstaticdata.dto.InstrumentDto
import com.exiasoft.itsstaticdata.mapper.InstrumentMapper
import com.exiasoft.itsstaticdata.model.Instrument
import com.exiasoft.itsstaticdata.service.ExchangeBoardService
import com.exiasoft.itsstaticdata.service.ExchangeService
import com.exiasoft.itsstaticdata.service.InstrumentService
import mu.KotlinLogging
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping(CONTEXT_PATH)
class InstrumentController(
        val exchangeService: ExchangeService,
        val exchangeBoardService: ExchangeBoardService,
        val instrumentService: InstrumentService,
        val instrumentMapper: InstrumentMapper
): BaseResource() {

    private val logger = KotlinLogging.logger {}

    @GetMapping("/sapi/exchanges/{exchangeCode}/instruments/{instrumentCode}")
    fun findOne(@PathVariable exchangeCode: String,
                @PathVariable instrumentCode: String,
                authenToken: AuthenticationToken,
                httpRequest: ServerHttpRequest): Mono<ResponseEntity<InstrumentDto>> {
        logger.debug("REST request to get a Instrument, exchangeCode = {}, instrumentCode = {}", exchangeCode, instrumentCode)
        val instrument = exchangeService.findByIdentifier(authenToken, exchangeCode).flatMap { exch ->
            exchangeBoardService.find(authenToken, exch.exchangeOid, null, null, PageUtil.unlimit()).flatMap { exchBoardList ->
                instrumentService.find(authenToken, exchBoardList.map { eb -> eb.exchangeBoardOid }.toSet(), instrumentCode, null, null, PageUtil.unlimit())            }
        }.flatMap { PageUtil.monoFromFirstOrEmpty(it) }
        logger.info("Instrument retrieved, result = {} ", instrument)
        return WebResponseUtil.wrapOrNotFound(instrumentMapper.modelToDto(authenToken, instrument))
    }

}

@RestController
@RequestMapping(CONTEXT_PATH_INTERNAL)
class InstrumentIntController(
        val instrumentService: InstrumentService
): BaseResource() {

    private val logger = KotlinLogging.logger {}

    @GetMapping("/sapi/instruments/{oid}")
    fun findByOid(@PathVariable oid: String,
                  authenToken: AuthenticationToken,
                  request: ServerHttpRequest): Mono<Instrument> {
        return instrumentService.findByOid(authenToken, oid)
    }

    @GetMapping("/sapi/instruments")
    fun find(@RequestParam(value = "exchangeBoardOid", required = false) exchangeBoardOid: List<String>?,
             @RequestParam(value = "instrumentCode", required = false) instrumentCode: String?,
             @RequestParam(value = "nameDefLang", required = false) nameDefLang: String?,
             @RequestParam(value = "status", required = false) status: String?,
             @PageableDefault(sort = ["instrumentOid,asc"]) pageable: Pageable,
             authenToken: AuthenticationToken,
             request: ServerHttpRequest
    ): Mono<PagingSearchResult<Instrument>> {
        val page = instrumentService.find(authenToken, exchangeBoardOid?.toSet(), instrumentCode, nameDefLang,
                if (status != null) EnumUtil.str2Enum(Status::class.java, status) else null, pageable)
        return page.map {
            WebResponseUtil.generatePaginationResponse(it, it.content)
        }
    }

    @PostMapping("/sapi/instruments")
    fun findByOids(@RequestBody oids: Set<String>,
                   @PageableDefault(sort = ["instrumentOid,asc"]) pageable: Pageable,
                   authenToken: AuthenticationToken,
                   request: ServerHttpRequest
    ): Mono<Map<String, Instrument>> {
        return instrumentService.findByOids(authenToken, oids)
    }

}