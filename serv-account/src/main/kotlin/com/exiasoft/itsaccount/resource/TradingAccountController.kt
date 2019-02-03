package com.exiasoft.itsaccount.resource

import com.exiasoft.itsaccount.CONTEXT_PATH
import com.exiasoft.itsaccount.dto.SimpleTradingAccountDto
import com.exiasoft.itsaccount.mapper.TradingAccountMapper
import com.exiasoft.itsaccount.service.TradingAccountService
import com.exiasoft.itscommon.annotation.PageableDefault
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.model.PagingSearchResult
import com.exiasoft.itscommon.resource.BaseResource
import com.exiasoft.itscommon.util.WebResponseUtil
import mu.KotlinLogging
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping(CONTEXT_PATH)
class TradingAccountController(
        val tradingAccountService: TradingAccountService,
        val tradingAccountMapper: TradingAccountMapper
): BaseResource() {

    private val logger = KotlinLogging.logger {}

    @PreAuthorize("@permissionEvaluator.hasPermission(authentication, '{ClientAccountGrp.ClientAccount.Maintenance}')")
    @GetMapping("/sapi/trading-accounts")
    fun find(@RequestParam(name = "tradingAccountCode", required = false) tradingAccountCode: String?,
             @RequestParam(name = "descptDefLang", required = false) nameOneDefLang: String?,
             @PageableDefault(sort = ["tradingAccountCode,asc"]) pageable: Pageable,
             authenToken: AuthenticationToken,
             request: ServerHttpRequest
    ): Mono<ResponseEntity<PagingSearchResult<SimpleTradingAccountDto>>> {

        logger.info("REST request to get a page of Trading Account")
        val page = tradingAccountService.find(authenToken, tradingAccountCode, nameOneDefLang, pageable)
        logger.info("Trading Account page retrieved, result = {} ", page)
        return WebResponseUtil.generatePaginationResponseEntity(request.path.toString(), page) { v -> tradingAccountMapper.modelToDto(authenToken, v) }
    }

}