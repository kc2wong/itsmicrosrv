package com.exiasoft.itsorder

import com.exiasoft.itsauthen.config.FunctionListConfig
import com.exiasoft.itsauthen.model.security.DataEntitlement
import com.exiasoft.itsauthen.service.DataEntitlementService
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.test.TestUtil
import com.exiasoft.itsorder.config.ItsOrderTestConfig
import com.exiasoft.itsorder.mapper.OrderMapper
import com.exiasoft.itsorder.resource.OrderController
import com.exiasoft.itsstaticdata.model.SimpleStock
import com.exiasoft.itsstaticdata.service.InstrumentService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.OverrideMockKs
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Import
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@ActiveProfiles("test")
@RunWith(JUnitPlatform::class)
@ExtendWith(SpringExtension::class, MockKExtension::class)
@Import(ItsOrderTestConfig::class)
@SpringBootTest(properties = ["spring.cloud.config.discovery.enabled:false"])
class OrderControllerTest {

    var client: WebTestClient? = null

    @MockK
    lateinit var dataEntitlementService: DataEntitlementService

    @MockK
    lateinit var instrumentService: InstrumentService

    @Autowired
    lateinit var functionListConfig: FunctionListConfig

    @Autowired
    lateinit var tokenProvider: TokenProvider

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    @OverrideMockKs
    lateinit var orderMapper: OrderMapper

    @Autowired
    @OverrideMockKs
    lateinit var orderController: OrderController

    private val URL = "/order-serv/v1/sapi/trading-accounts/306-1-61151-7/simple-orders"

    @BeforeEach
    fun setUp(applicationContext: ApplicationContext) {
        MockKAnnotations.init(this)
        objectMapper.registerModule(KotlinModule())
        client = WebTestClient
                .bindToApplicationContext(applicationContext)
                .configureClient()
                .build()

        val dataEntitlement = DataEntitlement()
        dataEntitlement.clientOid = "2017582"
        every {
            dataEntitlementService.getDataEntitlement(any())
        } returns Mono.just(dataEntitlement)

        val stock0005 = SimpleStock()
        stock0005.instrumentOid = "2036424"
        stock0005.instrumentCode = "00005"

        val stock0008 = SimpleStock()
        stock0008.instrumentOid = "2034125"
        stock0008.instrumentCode = "00008"

        val stock600000 = SimpleStock()
        stock600000.instrumentOid = "4197480"
        stock600000.instrumentCode = "600000"

        listOf(stock0005, stock0008, stock600000).forEach {
            every {
                instrumentService.findByOid(any(), it.instrumentOid)
            } returns Mono.just(it)
        }
    }

    @Test
    fun testOrderEnquiry() {
        val token = TestUtil.createToken(tokenProvider, functionListConfig.list.filter { it == "StaticDataGrp.LookupHelper" || it == "EnquiryGrp.Order.Enquiry"})

        val result = client!!.get().uri("$URL")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString())
                .header("Authorization", token.idToken)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.currentPage").isEqualTo("0")
                .jsonPath("$.totalCount").isEqualTo("18")
    }

}