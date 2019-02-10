package com.exiasoft.itsorder.resource

import com.exiasoft.itscommon.annotation.PageableDefault
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.model.PagingSearchResult
import com.exiasoft.itscommon.resource.BaseExperienceApiResource
import com.exiasoft.itscommon.util.WebResponseUtil
import com.exiasoft.itsorder.CONTEXT_PATH
import com.exiasoft.itsorder.dto.ChargeCommissionDto
import com.exiasoft.itsorder.dto.OrderDto
import com.exiasoft.itsorder.dto.OrderInputRequestDto
import com.exiasoft.itsorder.dto.SimpleOrderDto
import com.exiasoft.itsorder.mapper.ChargeCommissionMapper
import com.exiasoft.itsorder.mapper.OrderInputRequestMapper
import com.exiasoft.itsorder.mapper.OrderMapper
import com.exiasoft.itsorder.service.OrderService
import com.exiasoft.itsorder.service.SimpleOrderService
import mu.KotlinLogging
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.data.domain.Pageable
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.LocalDate

@RestController
@RequestMapping(CONTEXT_PATH)
class OrderController(
        val orderService: OrderService,
        val simpleOrderService: SimpleOrderService,
        val orderMapper: OrderMapper,
        val orderInputRequestMapper: OrderInputRequestMapper,
        val chargeCommissionMapper: ChargeCommissionMapper,
        loadBalancingClient: LoadBalancerClient,
        webClientBuilder: WebClient.Builder
): BaseExperienceApiResource(loadBalancingClient, webClientBuilder) {

    private val logger = KotlinLogging.logger {}

    @PreAuthorize("@permissionEvaluator.hasPermission(authentication, '{EnquiryGrp.Order.Enquiry}')")
    @GetMapping("/xapi/trading-accounts/{tradingAccountCode}/simple-orders")
    fun enquireOrder(@PathVariable tradingAccountCode: String,
                     @RequestParam(value = "exchangeCode", required = false) exchangeCode: String?,
                     @RequestParam(value = "startTradeDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") startTradeDate: LocalDate?,
                     @RequestParam(value = "endTradeDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") endTradeDate: LocalDate?,
                     @RequestParam (value="orderStatus", required = false) orderStatus: String?,
                     @PageableDefault(sort = ["orderNumber,asc"]) pageable: Pageable,
                     authenToken: AuthenticationToken,
                     request: ServerHttpRequest
    ): Mono<ResponseEntity<PagingSearchResult<SimpleOrderDto>>> {
        logger.trace("REST request to get a page of Simple Order")
        val page = simpleOrderService.enquireOrder(authenToken, tradingAccountCode,exchangeCode, startTradeDate, endTradeDate, emptySet(), pageable)
        logger.info("Simple Order page retrieved, result = {} ", page)
        return page.flatMap {
            val instrumentCodeMap = it.content.groupBy { it.exchangeCode }.mapValues { it.value.map { it.instrumentCode } }.mapValues { it.value.toSet() }
            // Consume get instrument API (once per Exchange) and merge the result with portfolio
            val requestParams = instrumentCodeMap.map {
                mapOf("exchangeCode" to it.key, "instrumentCode" to it.value.joinToString(","))
            }.toList()
            consumeResourceAndCollect(authenToken, "itsstaticdata", "/staticdata/v1/sapi/instruments", requestParams).flatMap { extResource ->
                WebResponseUtil.generatePaginationResponseEntity(request.path.toString(), page, mapOf("instruments" to extResource)) { v -> orderMapper.simpleModelToDto(authenToken, v) }
            }
        }
    }

    @PreAuthorize("@permissionEvaluator.hasPermission(authentication, '{EnquiryGrp.Order.Enquiry}')")
    @GetMapping("/sapi/orders/{orderNo}")
    fun findOne(@PathVariable orderNo: String,
                authenToken: AuthenticationToken
    ): Mono<ResponseEntity<OrderDto>> {
        logger.info("REST request to get an order, orderNo = {}", orderNo)
        val order = orderService.findByIdentifier(authenToken, orderNo)
        logger.trace("Order retrieved, result = {} ", order)
        return WebResponseUtil.wrapOrNotFound(order.flatMap { orderMapper.modelToDto(authenToken, it) })
    }

    @PreAuthorize("@permissionEvaluator.hasPermission(authentication, '{OrderGrp.Order.Capture}')")
    @PostMapping("/papi/simulations")
    fun calculateChargeCommission(@RequestBody orderInputRequestDto: OrderInputRequestDto,
                                  authenToken: AuthenticationToken,
                                  request: ServerHttpRequest
    ): Mono<ResponseEntity<ChargeCommissionDto>> {
        logger.trace("OrderResource.calculateChargeCommission() starts")
        return orderInputRequestMapper.dtoToModel(authenToken, orderInputRequestDto).flatMap {
            val chargeCommission = orderService.calculateChargeCommission(authenToken, it)
            WebResponseUtil.wrapOrNotFound(chargeCommissionMapper.modelToDto(authenToken, chargeCommission))
        }
    }

}