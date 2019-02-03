package com.exiasoft.itscommon.serializer

import com.exiasoft.itscommon.constant.JACKSON_DATETIME_PATTERN_STRING_FORMAT
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.time.LocalDateTime

class LocalDateTimeSerializer : StdSerializer<LocalDateTime>(LocalDateTime::class.java) {

    override fun serialize(value: LocalDateTime?, gen: JsonGenerator, serializers: SerializerProvider) {
        value?.let {
            v -> gen.writeString(String.format(JACKSON_DATETIME_PATTERN_STRING_FORMAT, v.year, v.monthValue, v.dayOfMonth, v.hour, v.minute, v.second))
        }
    }
}
