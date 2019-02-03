package com.exiasoft.itscommon.serializer

import com.exiasoft.itscommon.util.EnumUtil
import com.exiasoft.itscommon.util.ObjectUtil
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer

abstract class AbstractEnum2StringDeserializer<T : Enum<T>>(private val enumClass: Class<T>) : StdDeserializer<T>(enumClass) {
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): T? {
        val text = ObjectUtil.nvl(p?.text, "")
        return if (text.isBlank()) null else EnumUtil.str2Enum(enumClass, text)
    }
}