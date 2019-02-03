package com.exiasoft.itscommon.bean

import com.exiasoft.itscommon.converter.StripTrailingZeroBigDecimalConverter
import com.exiasoft.itscommon.service.msg.ItsDoData
import com.exiasoft.itscommon.service.msg.ItsMsg
import com.exiasoft.itscommon.service.msg.ItsMsgBody
import com.exiasoft.itscommon.service.msg.ItsMsgHeader
import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.naming.NoNameCoder
import com.thoughtworks.xstream.io.xml.DomDriver

class XStreamProvider {
    private val xstream = XStream(DomDriver("UTF8", NoNameCoder()))
    private val xstreamForSimpleObject = XStream(DomDriver("UTF8", NoNameCoder()))

    init {
        listOf(xstream, xstreamForSimpleObject).forEach {
            XStream.setupDefaultSecurity(it) // to be removed after 1.5
            it.allowTypesByWildcard(arrayOf(
                    "com.exiasoft.**"
            ))
            it.registerConverter(StripTrailingZeroBigDecimalConverter())
            it.ignoreUnknownElements()
            it.processAnnotations(ItsDoData::class.java)
            it.processAnnotations(ItsMsgBody::class.java)
            it.processAnnotations(ItsMsg::class.java)
            it.processAnnotations(ItsMsgHeader::class.java)
        }
    }

    fun provideXstream(): XStream {
        return xstream
    }

    fun provideXstreamForSimpleObject(): XStream {
        return xstreamForSimpleObject
    }
}
