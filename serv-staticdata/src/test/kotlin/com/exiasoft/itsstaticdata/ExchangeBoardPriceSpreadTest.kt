package com.exiasoft.itsstaticdata

import com.exiasoft.itsauthen.config.FunctionListConfig
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.test.TestUtil
import com.exiasoft.itsstaticdata.model.ExchangeBoardPriceSpread
import com.exiasoft.itsstaticdata.service.ExchangeBoardPriceSpreadService
import org.hamcrest.Matchers.hasSize
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient


@ActiveProfiles("test")
@RunWith(JUnitPlatform::class)
@ExtendWith(SpringExtension::class)
@ComponentScan("com.exiasoft.itscommon.config", "com.exiasoft.itsauthen", "com.exiasoft.itsstaticdata")
@SpringBootTest(properties = ["spring.cloud.config.discovery.enabled:false"])
class ExchangeBoardPriceSpreadTest {


    @Autowired
    lateinit var exchangeBoardPriceSpreadService: ExchangeBoardPriceSpreadService

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var tokenProvider: TokenProvider

    @Autowired
    lateinit var functionListConfig: FunctionListConfig

    private val NUM_OF_RECORD = 21

    @Test
    fun testExchangeBoardPriceSpreadServiceDemoImpl() {
        val token = TestUtil.createToken(tokenProvider, functionListConfig.list)
        var result: Page<ExchangeBoardPriceSpread>?

        // Find all
        // pageSize > # of records
        result = exchangeBoardPriceSpreadService.findAll(token, PageRequest.of(0, Short.MAX_VALUE.toInt())).block()
        result?.let {
            Assertions.assertEquals(Short.MAX_VALUE.toInt(), it.size)
            Assertions.assertEquals(NUM_OF_RECORD, it.content.size)
            Assertions.assertEquals(NUM_OF_RECORD, it.totalElements.toInt())
        }

        // pageSize = # of records
        result = exchangeBoardPriceSpreadService.findAll(token, PageRequest.of(0, NUM_OF_RECORD)).block()
        result?.let {
            Assertions.assertEquals(NUM_OF_RECORD, it.size)
            Assertions.assertEquals(NUM_OF_RECORD, it.content.size)
            Assertions.assertEquals(NUM_OF_RECORD, it.totalElements.toInt())
        }

        // pageSize < # of records
        result = exchangeBoardPriceSpreadService.findAll(token, PageRequest.of(0, 20)).block()
        result?.let {
            Assertions.assertEquals(20, it.size)
            Assertions.assertEquals(20, it.content.size)
            Assertions.assertEquals(NUM_OF_RECORD, it.totalElements.toInt())
        }
    }

    @Test
    @WithMockUser()
    fun testExchangeBoardPriceSpreadController() {
        val URL = "/static-data/v1/sapi/exchanges/HKG/exchange-boards/MAIN/price-spreads/1"
        val token = TestUtil.createToken(tokenProvider, functionListConfig.list)

        val result = webTestClient.get().uri(URL)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString())
                .header("Authorization", token.idToken)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.exchangeBoardCode").isEqualTo("MAIN")
                .jsonPath("$.exchangeBoardPriceSpreadCode").isEqualTo("1")
                .jsonPath("$.exchangeBoardPriceSpreadDetail", hasSize<Any>(NUM_OF_RECORD))

    }
}
