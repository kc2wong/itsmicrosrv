package com.exiasoft.itsorder.resource

import com.exiasoft.itscommon.annotation.PageableDefault
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.model.PagingSearchResult
import com.exiasoft.itscommon.resource.BaseResource
import com.exiasoft.itscommon.util.WebResponseUtil
import com.exiasoft.itsorder.CONTEXT_PATH
import com.exiasoft.itsorder.dto.SimpleOrderDto
import com.exiasoft.itsorder.mapper.OrderMapper
import com.exiasoft.itsorder.service.SimpleOrderService
import com.exiasoft.itsstaticdata.service.InstrumentService
import mu.KotlinLogging
import org.springframework.data.domain.Pageable
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.time.LocalDate

@RestController
@RequestMapping(CONTEXT_PATH)
class OrderController(
//        val orderService: OrderService,
        val simpleOrderService: SimpleOrderService,
        val instrumentService: InstrumentService,
        val orderMapper: OrderMapper
): BaseResource() {

    private val logger = KotlinLogging.logger {}

    @PreAuthorize("@permissionEvaluator.hasPermission(authentication, '{EnquiryGrp.Order.Enquiry}')")
    @GetMapping("/sapi/trading-accounts/{tradingAccountCode}/simple-orders")
    fun enquireOrder(@PathVariable tradingAccountCode: String,
                     @RequestParam(value = "exchangeCode", required = false) exchangeCode: String?,
                     @RequestParam(value = "startTradeDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") startTradeDate: LocalDate?,
                     @RequestParam(value = "endTradeDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") endTradeDate: LocalDate?,
                     @RequestParam (value="orderStatus", required = false) orderStatus: String?,
                     @PageableDefault(sort = ["orderNumber,asc"]) pageable: Pageable,
                     authenToken: AuthenticationToken,
                     request: ServerHttpRequest
    ): Mono<ResponseEntity<PagingSearchResult<SimpleOrderDto>>> {

        logger.debug("REST request to get a page of Simple Order")
        val page = simpleOrderService.enquireOrder(authenToken, tradingAccountCode,exchangeCode, startTradeDate, endTradeDate, emptySet(), pageable)
        logger.info("Simple Order page retrieved, result = {} ", page)
        return WebResponseUtil.generatePaginationResponseEntity(request.path.toString(), page) { v -> orderMapper.modelToDto(authenToken, v) }
    }
}