package com.exiasoft.itscommon.service.msg

import com.exiasoft.itscommon.converter.AbstractEnum2StringConverter
import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamConverter

class ItsMsgHeader {

    enum class Result(val value: String) {
        Success("CL_SUCCESS"), Fail("CL_FAIL");

        override fun toString(): String {
            return this.value
        }
    }

    @XStreamAsAttribute @XStreamAlias("CL_RESULT") @XStreamConverter(ResultConverter::class)
    var result: Result? = null

    @XStreamAsAttribute @XStreamAlias("CL_ERR_CODE")
    var errorCode: String? = null

    @XStreamAsAttribute @XStreamAlias("CL_ERR_PARA")
    var errorPara: String? = null

    class ResultConverter : AbstractEnum2StringConverter<Result>(Result::class.java)
}