package com.exiasoft.itsauthen

import com.exiasoft.itsauthen.config.FunctionListConfig
import com.exiasoft.itsauthen.config.ItsAuthenTestConfig
import com.exiasoft.itsauthen.service.DataEntitlementService
import com.exiasoft.itsauthen.service.UserProfileService
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.test.TestUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ActiveProfiles("test")
@RunWith(JUnitPlatform::class)
@ExtendWith(SpringExtension::class)
@Import(ItsAuthenTestConfig::class)
@SpringBootTest(properties = ["spring.cloud.config.discovery.enabled:false"])
class ItsauthenApplicationTests {

    @Autowired
    lateinit var userProfileService: UserProfileService

    @Autowired
    lateinit var dataEntitlementService: DataEntitlementService

    @Autowired
    lateinit var functionListConfig: FunctionListConfig

    @Autowired
    lateinit var tokenProvider: TokenProvider

    @Test
	fun testDemoFunctionImpl() {
        val token = TestUtil.createToken(tokenProvider, functionListConfig.list)

        val accessibleFunctions = userProfileService.getAccessibileFunctions(token).block()
        Assertions.assertNotNull(accessibleFunctions)
        Assertions.assertEquals(97, accessibleFunctions!!.size)
    }

    @Test
    fun testDataEntitlementServiceImpl() {
        val token = TestUtil.createToken(tokenProvider, functionListConfig.list)

        val dataEntitlement = dataEntitlementService.getDataEntitlement(token).block()
        Assertions.assertNotNull(dataEntitlement)
        dataEntitlement?.let { de ->
            Assertions.assertEquals("2017582", de.clientOid)
            Assertions.assertFalse(de.allAeForOrderApproval)
            Assertions.assertFalse(de.allowShortSellApproval)
        }
    }

}
