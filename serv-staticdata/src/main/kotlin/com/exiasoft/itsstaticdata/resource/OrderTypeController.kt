package com.exiasoft.itsstaticdata.resource

import com.exiasoft.itscommon.annotation.PageableDefault
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.model.PagingSearchResult
import com.exiasoft.itscommon.resource.BaseResource
import com.exiasoft.itscommon.util.WebResponseUtil
import com.exiasoft.itsstaticdata.CONTEXT_PATH
import com.exiasoft.itsstaticdata.CONTEXT_PATH_INTERNAL
import com.exiasoft.itsstaticdata.dto.OrderTypeDto
import com.exiasoft.itsstaticdata.mapper.OrderTypeMapper
import com.exiasoft.itsstaticdata.model.OrderType
import com.exiasoft.itsstaticdata.service.OrderTypeService
import mu.KotlinLogging
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping(CONTEXT_PATH)
class OrderTypeController(
        val orderTypeService: OrderTypeService,
        val orderTypeMapper: OrderTypeMapper
): BaseResource() {

    private val logger = KotlinLogging.logger {}

    @PreAuthorize("@permissionEvaluator.hasPermission(authentication, '{StaticDataGrp.OrderType.Maintenance,StaticDataGrp.LookupHelper}')")
    @GetMapping("/sapi/order-types")
    fun find(@RequestParam(name = "descptDefLang", required = false) descptDefLang: String?,
             @PageableDefault(sort = ["orderTypeCode,asc"]) pageable: Pageable,
            authenToken: AuthenticationToken,
            request: ServerHttpRequest
    ): Mono<ResponseEntity<PagingSearchResult<OrderTypeDto>>> {
        logger.debug("REST request to get a page of OrderType")
        val page = orderTypeService.find(authenToken, descptDefLang, pageable)
        logger.info("OrderType page retrieved, result = {} ", page)
        return WebResponseUtil.generatePaginationResponseEntity(request.path.toString(), page) { v -> orderTypeMapper.modelToDto(authenToken, v) }
    }

    @PreAuthorize("@permissionEvaluator.hasPermission(authentication, '{StaticDataGrp.OrderType.Maintenance,StaticDataGrp.LookupHelper}')")
    @GetMapping("/sapi/order-types/{orderTypeCode}")
    fun findOne(@PathVariable orderTypeCode: String,
                authenToken: AuthenticationToken,
                request: ServerHttpRequest
    ): Mono<ResponseEntity<OrderTypeDto>> {
        logger.debug("REST request to get a OrderType, orderTypeCode = {}", orderTypeCode)
        val orderType = orderTypeService.findByIdentifier(authenToken, orderTypeCode)
        logger.info("OrderType retrieved, result = {} ", orderType)
        return WebResponseUtil.wrapOrNotFound(orderType.map { orderTypeMapper.modelToDto(authenToken, it) })
    }

}

@RestController
@RequestMapping(CONTEXT_PATH_INTERNAL)
class OrderTypeIntController(
        val orderTypeService: OrderTypeService
): BaseResource() {

    private val logger = KotlinLogging.logger {}

    @GetMapping("/sapi/order-types/{oid}")
    fun findByOid(@PathVariable oid: String,
                  authenToken: AuthenticationToken,
                  request: ServerHttpRequest): Mono<OrderType> {
        return orderTypeService.findByOid(authenToken, oid)
    }

    @GetMapping("/sapi/order-types")
    fun find(@RequestParam(value = "descptDefLang", required = false) descptDefLang: String?,
             @PageableDefault(sort = ["orderTypeOid,asc"]) pageable: Pageable,
             authenToken: AuthenticationToken,
             request: ServerHttpRequest
    ): Mono<PagingSearchResult<OrderType>> {
        return orderTypeService.find(authenToken, descptDefLang, pageable).map {
            PagingSearchResult(it, it.content)
        }
    }

    @PostMapping("/sapi/order-types")
    fun findByOids(@RequestBody oids: Set<String>,
                   @PageableDefault(sort = ["orderTypeOid,asc"]) pageable: Pageable,
                   authenToken: AuthenticationToken,
                   request: ServerHttpRequest
    ): Mono<Map<String, OrderType>> {
        return orderTypeService.findByOids(authenToken, oids)
    }

}