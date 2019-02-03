package com.exiasoft.itsorder

import com.exiasoft.itsauthen.config.FunctionListConfig
import com.exiasoft.itsauthen.model.security.DataEntitlement
import com.exiasoft.itsauthen.service.DataEntitlementService
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.bean.XStreamProvider
import com.exiasoft.itscommon.test.TestUtil
import com.exiasoft.itscommon.util.PageUtil
import com.exiasoft.itsorder.config.ItsOrderTestConfig
import com.exiasoft.itsorder.service.SimpleOrderService
import com.exiasoft.itsorder.service.impl.server.demo.SimpleOrderServiceDemoImpl
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
@Import(ItsOrderTestConfig::class)
@SpringBootTest(properties = ["spring.cloud.config.discovery.enabled:false"])
class OrderServiceTest {

    @MockK
    lateinit var dataEntitlementService: DataEntitlementService

    lateinit var simpleOrderService: SimpleOrderService

    @Autowired
    lateinit var functionListConfig: FunctionListConfig

    @Autowired
    lateinit var tokenProvider: TokenProvider

    @Autowired
    lateinit var xStreamProvider: XStreamProvider

    @BeforeEach
    fun setUp() {
        val dataEntitlement = DataEntitlement()
        dataEntitlement.clientOid = "2017582"
        every {
            dataEntitlementService.getDataEntitlement(any())
        } returns Mono.just(dataEntitlement)

        simpleOrderService = SimpleOrderServiceDemoImpl(tokenProvider, xStreamProvider, dataEntitlementService)
    }

    @Test
    fun testTradingServiceDemoImpl() {
        val token = TestUtil.createToken(tokenProvider, functionListConfig.list)
        var orderPage = simpleOrderService.enquireOrder(token, "306-1-61151-7", null, null, null, emptySet(), PageUtil.unlimit()).block()
        Assertions.assertNotNull(orderPage)
        orderPage?.let {
            Assertions.assertEquals(18, it.content.size)
        }

        orderPage = simpleOrderService.enquireOrder(token, "306-1-61151-7", "HKG", null, null, emptySet(), PageUtil.unlimit()).block()
        Assertions.assertNotNull(orderPage)
        orderPage?.let {
            Assertions.assertEquals(17, it.content.size)
        }

    }

}