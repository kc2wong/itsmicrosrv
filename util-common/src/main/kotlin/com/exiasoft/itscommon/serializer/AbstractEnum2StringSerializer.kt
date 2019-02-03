package com.exiasoft.itscommon.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

abstract  class AbstractEnum2StringSerializer<T : Enum<T>>() : JsonSerializer<T>() {
    override fun serialize(value: T?, gen: JsonGenerator, serializers: SerializerProvider) {
        value?.let {
            gen.writeString(it.toString())
        }
    }
}
