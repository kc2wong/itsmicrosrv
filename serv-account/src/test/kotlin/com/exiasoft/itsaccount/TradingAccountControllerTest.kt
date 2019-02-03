package com.exiasoft.itsaccount

import com.exiasoft.itsaccount.config.ItsAccountTestConfig
import com.exiasoft.itsaccount.mapper.TradingAccountMapper
import com.exiasoft.itsaccount.resource.TradingAccountController
import com.exiasoft.itsaccount.service.TradingAccountService
import com.exiasoft.itsauthen.config.FunctionListConfig
import com.exiasoft.itsauthen.model.security.DataEntitlement
import com.exiasoft.itsauthen.service.DataEntitlementService
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.test.TestUtil
import com.exiasoft.itsstaticdata.model.SimpleOperationUnit
import com.exiasoft.itsstaticdata.model.SimpleOperationUnitData
import com.exiasoft.itsstaticdata.model.TradingAccountType
import com.exiasoft.itsstaticdata.service.SimpleOperationUnitService
import com.exiasoft.itsstaticdata.service.TradingAccountTypeService
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
import org.springframework.data.domain.PageImpl
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@ActiveProfiles("test")
@RunWith(JUnitPlatform::class)
@ExtendWith(SpringExtension::class, MockKExtension::class)
@Import(ItsAccountTestConfig::class)
@SpringBootTest(properties = ["spring.cloud.config.discovery.enabled:false"])
class TradingAccountControllerTest {

    var client: WebTestClient? = null

    @MockK
    lateinit var dataEntitlementService: DataEntitlementService

    @MockK
    lateinit var tradingAccountTypeService: TradingAccountTypeService

    @MockK
    lateinit var simpleOperationUnitService: SimpleOperationUnitService

    @Autowired
    lateinit var functionListConfig: FunctionListConfig

    @Autowired
    lateinit var tokenProvider: TokenProvider

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    @OverrideMockKs
    lateinit var tradingAccountService: TradingAccountService

    @Autowired
    @OverrideMockKs
    lateinit var tradingAccountMapper: TradingAccountMapper

    @Autowired
    @OverrideMockKs
    lateinit var tradingAccountController: TradingAccountController

    private val URL = "$CONTEXT_PATH/sapi/trading-accounts"

    @BeforeEach
    fun setUp(applicationContext: ApplicationContext) {
        MockKAnnotations.init(this)
        objectMapper.registerModule(KotlinModule())
        client = WebTestClient
                .bindToApplicationContext(applicationContext)
                .configureClient()
                .build()
    }

    @Test
    fun findOwnedAccount() {
        val token = TestUtil.createToken(tokenProvider, functionListConfig.list.filter { it == "StaticDataGrp.LookupHelper" || it == "ClientAccountGrp.ClientAccount.Maintenance"})

        val dataEntitlement = DataEntitlement()
        dataEntitlement.clientOid = "2017582"
        every {
            dataEntitlementService.getDataEntitlement(any())
        } returns Mono.just(dataEntitlement)

        val marginAcctType = TradingAccountType()
        marginAcctType.tradingAccountTypeOid = "140003"
        marginAcctType.tradingAccountTypeCode = "M"
        val custodianAcctType = TradingAccountType()
        custodianAcctType.tradingAccountTypeOid = "140002"
        custodianAcctType.tradingAccountTypeCode = "U"

        listOf(marginAcctType, custodianAcctType).forEach {
            every {
                tradingAccountTypeService.findByOid(any(), it.tradingAccountTypeOid)
            } returns Mono.just(it)
        }
        every {
            tradingAccountTypeService.findAll(any(), any())
        } returns Mono.just(PageImpl(listOf(marginAcctType, custodianAcctType)))

        val operationUnitWlb = SimpleOperationUnitData()
        operationUnitWlb.operationUnitOid = "240001"
        operationUnitWlb.operationUnitCode = "WLB"

        listOf(operationUnitWlb).forEach {
            every {
                simpleOperationUnitService.findByOid(any(), it.operationUnitOid)
            } returns Mono.just(it)
        }
        every {
            simpleOperationUnitService.findAll(any(), any())
        } returns Mono.just(PageImpl(listOf(operationUnitWlb as SimpleOperationUnit)))

        client!!.get().uri("$URL")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString())
                .header("Authorization", token.idToken)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.currentPage").isEqualTo("0")
                .jsonPath("$.totalCount").isEqualTo("2")
                .jsonPath("$.content[0].tradingAccountCode").isEqualTo("306-1-61151-7")
                .jsonPath("$.content[0].tradingAccountTypeCode").isEqualTo("U")
                .jsonPath("$.content[0].operationUnitCode").isEqualTo("WLB")
                .jsonPath("$.content[1].tradingAccountCode").isEqualTo("306-1-61284-6")
                .jsonPath("$.content[1].tradingAccountTypeCode").isEqualTo("M")
                .jsonPath("$.content[1].operationUnitCode").isEqualTo("WLB")
    }

}