package com.exiasoft.itscommon.serializer

import com.exiasoft.itscommon.constant.JACKSON_DATE_PATTERN_STRING_FORMAT
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.time.LocalDate

class LocalDateSerializer : StdSerializer<LocalDate>(LocalDate::class.java) {

    override fun serialize(value: LocalDate?, gen: JsonGenerator, serializers: SerializerProvider) {
        value?.let {
            v -> gen.writeString(String.format(JACKSON_DATE_PATTERN_STRING_FORMAT, v.year, v.monthValue, v.dayOfMonth))
        }
    }
}
