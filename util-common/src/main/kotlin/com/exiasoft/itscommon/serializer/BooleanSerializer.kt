package com.exiasoft.itscommon.serializer

import com.exiasoft.itscommon.constant.STR_N
import com.exiasoft.itscommon.constant.STR_Y
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer

class BooleanSerializer : StdSerializer<Boolean>(Boolean::class.java) {

    override fun serialize(v: Boolean?, gen: JsonGenerator, serializers: SerializerProvider) {
        v?.let {
            gen.writeString(if (it) STR_Y else STR_N)
        }
    }
}
