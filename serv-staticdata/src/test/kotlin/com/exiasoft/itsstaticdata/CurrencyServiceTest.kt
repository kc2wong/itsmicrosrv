package com.exiasoft.itsstaticdata

import com.exiasoft.itsauthen.config.FunctionListConfig
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.test.TestUtil
import com.exiasoft.itsstaticdata.config.ItsStaticDataTestConfig
import com.exiasoft.itsstaticdata.model.Currency
import com.exiasoft.itsstaticdata.service.CurrencyService
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
class CurrencyServiceTest {

    @Autowired
    lateinit var currencyService: CurrencyService

    @Autowired
    lateinit var functionListConfig: FunctionListConfig

    @Autowired
    lateinit var tokenProvider: TokenProvider

    private val NUM_OF_RECORD = 27

    @Test
    fun testCurrencyServiceDemoImpl() {
        val token = TestUtil.createToken(tokenProvider, functionListConfig.list)
        var result: Page<Currency>?

        // Find all
        // pageSize > # of records
        result = currencyService.findAll(token, PageRequest.of(0, Short.MAX_VALUE.toInt())).block()
        result?.let {
            Assertions.assertEquals(Short.MAX_VALUE.toInt(), it.size)
            Assertions.assertEquals(NUM_OF_RECORD, it.content.size)
            Assertions.assertEquals(NUM_OF_RECORD, it.totalElements.toInt())
        }

        // pageSize = # of records
        result = currencyService.findAll(token, PageRequest.of(0, NUM_OF_RECORD)).block()
        result?.let {
            Assertions.assertEquals(NUM_OF_RECORD, it.size)
            Assertions.assertEquals(NUM_OF_RECORD, it.content.size)
            Assertions.assertEquals(NUM_OF_RECORD, it.totalElements.toInt())
        }

        // pageSize < # of records
        result = currencyService.findAll(token, PageRequest.of(0, 20)).block()
        result?.let {
            Assertions.assertEquals(20, it.size)
            Assertions.assertEquals(20, it.content.size)
            Assertions.assertEquals(NUM_OF_RECORD, it.totalElements.toInt())
        }

        // Find by currency name
        result = currencyService.find(token, "Hong Kong Dollar", PageRequest.of(0, Short.MAX_VALUE.toInt())).block()
        Assertions.assertNotNull(result)
        Assertions.assertEquals(1, result!!.totalElements)

        val currencyMap = currencyService.findByOids(token, setOf("150001", "150007")).block()
        Assertions.assertNotNull(currencyMap)
        currencyMap?.let {
            Assertions.assertEquals(2, it.size)
        }

        val currencyHkd = currencyService.findByOid(token, "150001").block()
        Assertions.assertNotNull(currencyHkd)

    }

}