package com.exiasoft.itscommon.serializer

import com.exiasoft.itscommon.constant.JACKSON_DATETIME_PATTERN_SIMPLE_DATE_FORMAT
import com.exiasoft.itscommon.util.ObjectUtil
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime

class LocalDateTimeDeserializer : StdDeserializer<LocalDateTime>(LocalDateTime::class.java) {

    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): LocalDateTime? {
        val text = ObjectUtil.nvl(p?.text, "")
        return if (text.isBlank()) null else {
            val sdf = SimpleDateFormat(JACKSON_DATETIME_PATTERN_SIMPLE_DATE_FORMAT)
            return (Timestamp(sdf.parse(text).time)).toLocalDateTime()
        }
    }
}
