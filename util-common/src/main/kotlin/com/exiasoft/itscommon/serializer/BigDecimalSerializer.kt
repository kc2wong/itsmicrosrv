package com.exiasoft.itscommon.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.math.BigDecimal

class BigDecimalSerializer : StdSerializer<BigDecimal>(BigDecimal::class.java) {

    override fun serialize(value: BigDecimal?, gen: JsonGenerator, serializers: SerializerProvider) {
        value?.let {
            v -> gen.writeString(v.toPlainString())
        }
    }
}
