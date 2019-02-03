package com.exiasoft.itscommon.converter

import com.thoughtworks.xstream.converters.basic.BigDecimalConverter
import java.math.BigDecimal

class StripTrailingZeroBigDecimalConverter : BigDecimalConverter() {

    override fun toString(obj: Any?): String? {
        return obj?.let { (it as BigDecimal).stripTrailingZeros().toPlainString() }
    }

    override fun fromString(str: String?): Any {
        return (super.fromString(str) as BigDecimal).stripTrailingZeros()
    }
}