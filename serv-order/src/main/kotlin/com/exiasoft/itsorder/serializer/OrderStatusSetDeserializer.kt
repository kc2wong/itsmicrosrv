package com.exiasoft.itsorder.serializer

import com.exiasoft.itscommon.util.ObjectUtil
import com.exiasoft.itsorder.model.enumeration.OrderStatus
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

class OrderStatusSetDeserializer : JsonDeserializer<Set<OrderStatus>>()  {

    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Set<OrderStatus>? {
        val text = ObjectUtil.nvl(p?.text, "")
        return if (text.isBlank()) null else {
            text?.let {
                it.split(",").map { s -> OrderStatus.valueOf(s) }.toSet()
            }
        }

    }
}
