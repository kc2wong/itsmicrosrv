package com.exiasoft.itscommon.serializer

import com.exiasoft.itscommon.util.ObjectUtil
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.math.BigDecimal

class BigDecimalDeserializer : StdDeserializer<BigDecimal>(BigDecimal::class.java) {

    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): BigDecimal? {
        val text = ObjectUtil.nvl(p?.text, "")
        return if (text.isBlank()) null else {
            BigDecimal(text).stripTrailingZeros()
        }
    }
}
