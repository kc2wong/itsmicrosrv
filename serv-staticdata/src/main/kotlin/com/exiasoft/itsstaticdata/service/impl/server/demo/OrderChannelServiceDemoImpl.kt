package com.exiasoft.itsstaticdata.service.impl.server.demo

import com.exiasoft.itscommon.annotation.ItsFunction
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.bean.XStreamProvider
import com.exiasoft.itscommon.service.server.demo.BaseDemoServiceImpl
import com.exiasoft.itsstaticdata.model.OrderChannel
import com.exiasoft.itsstaticdata.service.OrderChannelService
import org.springframework.stereotype.Service

@Service(value="OrderChannelServiceImpl")
@ItsFunction(["StaticDataGrp.OrderChnl.Maintenance", "StaticDataGrp.LookupHelper"])
class OrderChannelServiceDemoImpl(
        tokenProvider: TokenProvider,
        xStreamProvider: XStreamProvider
) : BaseDemoServiceImpl<String, OrderChannel>(tokenProvider, xStreamProvider, OrderChannel::class.java), OrderChannelService {

    override fun getOid(obj: OrderChannel): String {
        return obj.orderChannelOid
    }

    override fun getResourceName(): String {
        return "/service/demo/orderChannel.xml"
    }

    override fun isEqualsInIdentifier(id: String, obj: OrderChannel): Boolean {
        return id == obj.orderChannelCode
    }

}
