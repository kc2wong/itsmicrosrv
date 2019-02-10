package com.exiasoft.itsaccount.mapper

import com.exiasoft.itsaccount.dto.AccountPortfolioDto
import com.exiasoft.itsaccount.dto.CashPortfolioDto
import com.exiasoft.itsaccount.dto.SecurityPositionSummaryDto
import com.exiasoft.itsaccount.dto.TradingAccountPurchasePowerDto
import com.exiasoft.itsaccount.model.AccountPortfolio
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itsstaticdata.service.CurrencyService
import com.exiasoft.itsstaticdata.service.SimpleExchangeService
import com.exiasoft.itsstaticdata.service.SimpleOperationUnitService
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AccountPortfolioMapper(val operationUnitService: SimpleOperationUnitService, val exchangeService: SimpleExchangeService, val currencyService: CurrencyService) {

    fun modelToDto(authenToken: AuthenticationToken, accountPortfolio: Mono<AccountPortfolio>): Mono<AccountPortfolioDto> {
        return currencyService.findAll(authenToken, Pageable.unpaged()).zipWith(
                exchangeService.findAll(authenToken, Pageable.unpaged())).flatMap {

            val currencyMap = it.t1.content.map { v -> v.currencyOid to v }.toMap()
            val exchangeMap = it.t2.content.map { v -> v.exchangeOid to v }.toMap()

            accountPortfolio.map {ap ->
                val accountPortfolioDto = AccountPortfolioDto()
                BeanUtils.copyProperties(ap, accountPortfolioDto)

                accountPortfolioDto.securityPositionSummary = ap.securityPositionSummary.map {sp ->
                    val securityPositionSummaryDto = SecurityPositionSummaryDto()
                    BeanUtils.copyProperties(sp, securityPositionSummaryDto)
                    currencyMap[sp.currencyOid]?.let { securityPositionSummaryDto.currencyCode = it.currencyCode }
                    exchangeMap[sp.exchangeOid]?.let { securityPositionSummaryDto.exchangeCode = it.exchangeCode }
                    securityPositionSummaryDto
                }.toList()

                accountPortfolioDto.cashPortfolio = ap.cashPortfolio.map { cp ->
                    val cashPortfolioDto = CashPortfolioDto()
                    BeanUtils.copyProperties(cp, cashPortfolioDto)
                    cashPortfolioDto
                }.toList()

                accountPortfolioDto.purchasePower =
                        ap.purchasePower?.let { pp ->
                            val tradingAccountPurchasePowerDto = TradingAccountPurchasePowerDto()
                            BeanUtils.copyProperties(pp, tradingAccountPurchasePowerDto)
                            currencyMap[pp.creditRiskCurrencyOid]?.let { tradingAccountPurchasePowerDto.creditRiskCurrencyCode = it.currencyCode }
                            tradingAccountPurchasePowerDto
                        }

                accountPortfolioDto
            }
        }
    }

    fun modelToDto(authenToken: AuthenticationToken, accountPortfolio: AccountPortfolio): Mono<AccountPortfolioDto> {
        return modelToDto(authenToken, Mono.just(accountPortfolio))
    }

    fun <R> modelToDto(authenToken: AuthenticationToken, accountPortfolio: AccountPortfolio, transform: (accountPortfolioDto: AccountPortfolioDto) -> R): Mono<R> {
        return modelToDto(authenToken, Mono.just(accountPortfolio)).map { transform(it) }
    }

}