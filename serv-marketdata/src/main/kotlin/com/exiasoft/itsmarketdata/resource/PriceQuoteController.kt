package com.exiasoft.itsmarketdata.resource

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.resource.BaseResource
import com.exiasoft.itscommon.util.PageUtil
import com.exiasoft.itsmarketdata.CONTEXT_PATH
import com.exiasoft.itsmarketdata.dto.PriceQuoteDto
import com.exiasoft.itsmarketdata.mapper.PriceQuoteMapper
import com.exiasoft.itsmarketdata.service.PriceQuoteService
import com.exiasoft.itsstaticdata.service.ExchangeBoardService
import com.exiasoft.itsstaticdata.service.ExchangeService
import com.exiasoft.itsstaticdata.service.InstrumentService
import com.exiasoft.itsstaticdata.service.SimpleExchangeService
import mu.KotlinLogging
import org.springframework.data.domain.Pageable
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping(CONTEXT_PATH)
class PriceQuoteController(val exchangeService: SimpleExchangeService,
                           val exchangeBoardService: ExchangeBoardService,
                           val instrumentService: InstrumentService,
                           val priceQuoteService: PriceQuoteService,
                           val priceQuoteMapper: PriceQuoteMapper): BaseResource() {

    private val logger = KotlinLogging.logger {}

    @PreAuthorize("@permissionEvaluator.hasPermission(authentication, '{EnquiryGrp.InstrumentPrice.Enquiry}')")
    @GetMapping("/sapi/exchanges/{exchangeCode}/instruments/{instrumentCode}/quotes")
    fun findOne(@PathVariable exchangeCode: String,
                @PathVariable instrumentCode: String,
                authenToken: AuthenticationToken,
                httpRequest: ServerHttpRequest): Mono<PriceQuoteDto> {
        logger.trace("REST request to get a price quote, exchangeCode = {}, instrumentCode = {}", exchangeCode, instrumentCode)

        return exchangeService.findByIdentifier(authenToken, exchangeCode).flatMap { exch ->
            exchangeBoardService.find(authenToken, exch.exchangeOid, null, null, PageUtil.unlimit()).flatMap { exchBoardPage ->
                instrumentService.find(authenToken, exchBoardPage.content.map { exchBoard -> exchBoard.exchangeBoardOid }.toSet(), instrumentCode, null, null, Pageable.unpaged()).flatMap { instrumentPage ->
                    val instrument = instrumentPage.content.first()
                    priceQuoteMapper.modelToDto(authenToken, priceQuoteService.findByIdentifier(authenToken, instrument.identifier))
                }
            }
        }
    }

}
