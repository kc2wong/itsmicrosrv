package com.exiasoft.itsaccount.service.impl.server.demo

import com.exiasoft.itsaccount.model.AccountPortfolio
import com.exiasoft.itsaccount.model.CashPortfolio
import com.exiasoft.itsaccount.model.SecurityPositionSummary
import com.exiasoft.itsaccount.service.PortfolioEnquiryService
import com.exiasoft.itsaccount.service.TradingAccountService
import com.exiasoft.itscommon.annotation.ItsFunction
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.bean.XStreamProvider
import com.exiasoft.itscommon.service.server.demo.BaseDemoServiceImpl
import com.exiasoft.itsstaticdata.service.CurrencyService
import com.exiasoft.itsstaticdata.service.SimpleOperationUnitService
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service(value = "PortfolioEnquiryServiceImpl")
@ItsFunction(["EnquiryGrp.Portfolio.Enquiry"])
class PortfolioEnquiryServiceDemoImpl(tokenProvider: TokenProvider, xStreamProvider: XStreamProvider,
                                      val tradingAccountService: TradingAccountService, val currencyService: CurrencyService, val operationUnitService: SimpleOperationUnitService) :
        BaseDemoServiceImpl<AccountPortfolio.AccountPortfolioId, AccountPortfolio>(tokenProvider, xStreamProvider, AccountPortfolio::class.java, listOf(SecurityPositionSummary::class.java, CashPortfolio::class.java)),
        PortfolioEnquiryService {

    private val logger = KotlinLogging.logger {}

    override fun getResourceName(): String {
        return "/service/demo/tradingAccountPortfolio.xml"
    }

    override fun isEqualsInIdentifier(id: AccountPortfolio.AccountPortfolioId, obj: AccountPortfolio): Boolean {
        return id.tradingAccountCode == obj.tradingAccountCode && id.currencyCode == obj.currencyCode
    }

    override fun getOid(obj: AccountPortfolio): String {
        return "${obj.tradingAccountCode}-${obj.currencyCode}"
    }

}