package com.exiasoft.itscommon.serializer

import com.exiasoft.itscommon.constant.STR_N
import com.exiasoft.itscommon.constant.STR_Y
import com.exiasoft.itscommon.util.ObjectUtil
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer

class BooleanDeserializer : StdDeserializer<Boolean>(Boolean::class.java) {

    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Boolean? {
        val text = ObjectUtil.nvl(p?.text, "")
        return when (text) {
            STR_Y -> true
            STR_N -> false
            else -> null
        }
    }
}
