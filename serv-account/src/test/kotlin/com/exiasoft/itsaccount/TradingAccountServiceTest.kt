package com.exiasoft.itsaccount

import com.exiasoft.itsaccount.config.ItsAccountTestConfig
import com.exiasoft.itsaccount.service.TradingAccountService
import com.exiasoft.itsaccount.service.impl.server.demo.TradingAccountServiceDemoImpl
import com.exiasoft.itsauthen.config.FunctionListConfig
import com.exiasoft.itsauthen.model.security.DataEntitlement
import com.exiasoft.itsauthen.service.DataEntitlementService
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.bean.XStreamProvider
import com.exiasoft.itscommon.test.TestUtil
import com.exiasoft.itscommon.util.PageUtil
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Mono

@ActiveProfiles("test")
@RunWith(JUnitPlatform::class)
@ExtendWith(SpringExtension::class, MockKExtension::class)
@Import(ItsAccountTestConfig::class)
@SpringBootTest(properties = ["spring.cloud.config.discovery.enabled:false"])
class TradingAccountServiceTest {

    @MockK
    lateinit var dataEntitlementService: DataEntitlementService

    lateinit var tradingAccountService: TradingAccountService

    @Autowired
    lateinit var functionListConfig: FunctionListConfig

    @Autowired
    lateinit var tokenProvider: TokenProvider

    @Autowired
    lateinit var xStreamProvider: XStreamProvider

    @BeforeEach
    fun setUp() {
        tradingAccountService = TradingAccountServiceDemoImpl(tokenProvider, xStreamProvider, dataEntitlementService)
    }

    @Test
    fun testTradingAccountServiceDemoImpl() {
        val token = TestUtil.createToken(tokenProvider, functionListConfig.list)
        val dataEntitlement = DataEntitlement()
        dataEntitlement.clientOid = "2017582"
        every {
            dataEntitlementService.getDataEntitlement(token)
        } returns Mono.just(dataEntitlement)
        val tradingAccountPage = tradingAccountService.find(token, null, null, PageUtil.unlimit()).block()
        Assertions.assertNotNull(tradingAccountPage)
        tradingAccountPage?.let {
            Assertions.assertEquals(2, it.content.size)
        }
    }
}