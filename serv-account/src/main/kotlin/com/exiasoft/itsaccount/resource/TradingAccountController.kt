package com.exiasoft.itsaccount.resource

import com.exiasoft.itsaccount.CONTEXT_PATH
import com.exiasoft.itsaccount.dto.AccountPortfolioDto
import com.exiasoft.itsaccount.dto.SimpleTradingAccountDto
import com.exiasoft.itsaccount.mapper.AccountPortfolioMapper
import com.exiasoft.itsaccount.mapper.TradingAccountMapper
import com.exiasoft.itsaccount.model.AccountPortfolio
import com.exiasoft.itsaccount.service.PortfolioEnquiryService
import com.exiasoft.itsaccount.service.TradingAccountService
import com.exiasoft.itscommon.annotation.PageableDefault
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.HEADER_AUTHORIZATION
import com.exiasoft.itscommon.model.PagingSearchResult
import com.exiasoft.itscommon.resource.BaseExperienceApiResource
import com.exiasoft.itscommon.util.WebResponseUtil
import com.exiasoft.itsstaticdata.service.CurrencyService
import com.exiasoft.itsstaticdata.service.SimpleExchangeService
import mu.KotlinLogging
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping(CONTEXT_PATH)
class TradingAccountController(
        val currencyService: CurrencyService,
        val simpleExchangeService: SimpleExchangeService,
        val tradingAccountService: TradingAccountService,
        val portfolioEnquiryService: PortfolioEnquiryService,
        val tradingAccountMapper: TradingAccountMapper,
        val portfolioMapper: AccountPortfolioMapper,
        loadBalancingClient: LoadBalancerClient,
        webClientBuilder: WebClient.Builder
): BaseExperienceApiResource(loadBalancingClient, webClientBuilder) {

    private val logger = KotlinLogging.logger {}

    @PreAuthorize("@permissionEvaluator.hasPermission(authentication, '{ClientAccountGrp.ClientAccount.Search}')")
    @GetMapping("/sapi/trading-accounts")
    fun find(@RequestParam(name = "tradingAccountCode", required = false) tradingAccountCode: String?,
             @RequestParam(name = "descptDefLang", required = false) nameOneDefLang: String?,
             @PageableDefault(sort = ["tradingAccountCode,asc"]) pageable: Pageable,
             authenToken: AuthenticationToken,
             request: ServerHttpRequest
    ): Mono<ResponseEntity<PagingSearchResult<SimpleTradingAccountDto>>> {

        logger.trace("REST request to get a page of Trading Account")
        val page = tradingAccountService.find(authenToken, tradingAccountCode, nameOneDefLang, pageable)
        logger.info("Trading Account page retrieved, result = {} ", page)
        return WebResponseUtil.generatePaginationResponseEntity(request.path.toString(), page) { v -> tradingAccountMapper.modelToDto(authenToken, v) }
    }

    @GetMapping("/xapi/trading-accounts/{tradingAccountCode}/currencies/{currencyCode}/portfolios")
    fun getAccountPortfolio(authenToken: AuthenticationToken,
            @PathVariable tradingAccountCode: String,
            @PathVariable currencyCode: String
    ): Mono<ResponseEntity<AccountPortfolioBundle>> {
        logger.trace("TradingAccountController.getAccountPortfolio() starts, tradingAccountCode = ${tradingAccountCode} , currencyCode = ${currencyCode} ")

        return portfolioEnquiryService.findByIdentifier(authenToken, AccountPortfolio.AccountPortfolioId(tradingAccountCode, currencyCode)).flatMap { acctPortfolio ->
            // convert securities position to map of <ExchangeCode, List of Instrument Code>
            val instrumentCodeMap = acctPortfolio.securityPositionSummary.groupBy { it.exchangeOid }.mapValues { it.value.map { it.instrumentCode } }
            simpleExchangeService.findAll(authenToken, Pageable.unpaged()).flatMap {
                // Consume get instrument API (once per Exchange) and merge the result with portfolio
                val requestParams = it.content.map {
                    mapOf("exchangeCode" to it.exchangeCode, "instrumentCode" to instrumentCodeMap[it.exchangeOid]!!.joinToString(","))
                }.toList()
                consumeResourceAndCollect(authenToken, "itsstaticdata", "/staticdata/v1/sapi/instruments", requestParams).flatMap { extResource ->
                    val portfolio = portfolioMapper.modelToDto(authenToken, acctPortfolio) { AccountPortfolioBundle(it, extResource.asSequence().toList())}
                    WebResponseUtil.wrapOrNotFound(portfolio!!)
                }
            }
        }
    }

    data class AccountPortfolioBundle(val accountPortfolio: AccountPortfolioDto, val instruments: List<Any>)
}