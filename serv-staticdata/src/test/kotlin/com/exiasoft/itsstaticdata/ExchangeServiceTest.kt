package com.exiasoft.itsstaticdata

import com.exiasoft.itsauthen.config.FunctionListConfig
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.test.TestUtil
import com.exiasoft.itsstaticdata.config.ItsStaticDataTestConfig
import com.exiasoft.itsstaticdata.model.SimpleExchange
import com.exiasoft.itsstaticdata.service.SimpleExchangeService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ActiveProfiles("test")
@RunWith(JUnitPlatform::class)
@ExtendWith(SpringExtension::class)
@Import(ItsStaticDataTestConfig::class)
@SpringBootTest(properties = ["spring.cloud.config.discovery.enabled:false"])
class ExchangeServiceTest {

    @Autowired
    lateinit var exchangeService: SimpleExchangeService

    @Autowired
    lateinit var tokenProvider: TokenProvider

    @Autowired
    lateinit var functionListConfig: FunctionListConfig

    private val NUM_OF_RECORD = 3

	@Test
	fun testExchangeServiceDemoImpl() {
        val token = TestUtil.createToken(tokenProvider, functionListConfig.list)
        var result: Page<SimpleExchange>?

        // Find all
        // pageSize > # of records
		result = exchangeService.find(token, null, PageRequest.of(0, Short.MAX_VALUE.toInt())).block()
		Assertions.assertNotNull(result)
        result?.let {
            Assertions.assertEquals(Short.MAX_VALUE.toInt(), it.size)
            Assertions.assertEquals(2, it.content.size)
            Assertions.assertEquals(2, it.totalElements)
        }

        // pageSize = # of records
        result = exchangeService.find(token, null, PageRequest.of(0, 2)).block()
        Assertions.assertNotNull(result)
        result?.let {
            Assertions.assertEquals(2, it.size)
            Assertions.assertEquals(2, it.content.size)
            Assertions.assertEquals(2, it.totalElements)
        }

        // pageSize < # of records
        result = exchangeService.find(token, null, PageRequest.of(0, 1)).block()
        Assertions.assertNotNull(result)
        result?.let {
            Assertions.assertEquals(1, it.size)
            Assertions.assertEquals(1, it.content.size)
            Assertions.assertEquals(2, it.totalElements)
        }


        // Find by exchange name
        result = exchangeService.find(token, "Mainland A-share Market", PageRequest.of(0, Short.MAX_VALUE.toInt())).block()
        Assertions.assertNotNull(result)
        Assertions.assertEquals(1, result!!.totalElements)

        result = exchangeService.find(token, "Mainland A-share Market2", PageRequest.of(0, Short.MAX_VALUE.toInt())).block()
        Assertions.assertNotNull(result)
        Assertions.assertEquals(0, result!!.totalElements)

	}

}
