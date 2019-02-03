package com.exiasoft.itsstaticdata

import com.exiasoft.itsauthen.config.FunctionListConfig
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.test.TestUtil
import com.exiasoft.itscommon.test.TestUtil.Companion.basicResponseDoc
import com.exiasoft.itscommon.test.TestUtil.Companion.defaultRestDocument
import com.exiasoft.itscommon.test.TestUtil.Companion.pageResponseDoc
import com.exiasoft.itsstaticdata.config.ItsStaticDataTestConfig
import com.exiasoft.itsstaticdata.dto.SimpleExchangeDto
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
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
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ActiveProfiles("test")
@RunWith(JUnitPlatform::class)
@ExtendWith(value = [RestDocumentationExtension::class, SpringExtension::class])
@Import(ItsStaticDataTestConfig::class)
@SpringBootTest(properties = ["spring.cloud.config.discovery.enabled:false"])
class ExchangeControllerTest {

    var client: WebTestClient? = null

    @Autowired
    lateinit var functionListConfig: FunctionListConfig

    @Autowired
    lateinit var tokenProvider: TokenProvider

    @Autowired
    lateinit var objectMapper: ObjectMapper

    private val NUM_OF_RECORD = 3

    private val URL = "$CONTEXT_PATH/sapi/exchanges"

    @BeforeEach
    fun setUp(applicationContext: ApplicationContext, restDocumentation: RestDocumentationContextProvider) {
        objectMapper.registerModule(KotlinModule())
        client = WebTestClient
                .bindToApplicationContext(applicationContext)
                .configureClient()
                .filter(defaultRestDocument(applicationContext, restDocumentation))
                .build()
    }

    @Test
    fun findAll() {
        val token = TestUtil.createToken(tokenProvider, functionListConfig.list.filter { setOf("StaticDataGrp.Exchange.Maintenance", "StaticDataGrp.LookupHelper").contains(it) })
        val result = client!!.get().uri(URL)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString())
                .header("Authorization", token.idToken)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.currentPage").isEqualTo("0")
                .jsonPath("$.totalPage").isEqualTo("1")
                .consumeWith(basicResponseDoc(objectMapper, SimpleExchangeDto::class.java))
    }

    @Test
    fun search() {
        val token = TestUtil.createToken(tokenProvider, functionListConfig.list.filter { setOf("StaticDataGrp.Exchange.Maintenance", "StaticDataGrp.LookupHelper").contains(it) })
        val result = client!!.get().uri { it.path(URL).queryParam("nameDefLang", "exchange").build() }
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString())
                .header("Authorization", token.idToken)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.currentPage").isEqualTo("0")
                .jsonPath("$.totalPage").isEqualTo("1")
                .jsonPath("$.totalCount").isEqualTo("2")
                .consumeWith(pageResponseDoc(objectMapper, SimpleExchangeDto::class.java))

    }

    @Test
    fun findOne() {
        val token = TestUtil.createToken(tokenProvider, functionListConfig.list.filter { setOf("StaticDataGrp.Exchange.Maintenance", "StaticDataGrp.LookupHelper").contains(it) })
        val result = client!!.get().uri("$URL/HKG")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString())
                .header("Authorization", token.idToken)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.exchangeCode").isEqualTo("HKG")
                .consumeWith(basicResponseDoc(objectMapper, SimpleExchangeDto::class.java))

    }
}