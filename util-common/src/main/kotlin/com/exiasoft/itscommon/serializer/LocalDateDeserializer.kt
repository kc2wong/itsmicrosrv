package com.exiasoft.itscommon.serializer

import com.exiasoft.itscommon.constant.JACKSON_DATE_PATTERN_SIMPLE_DATE_FORMAT
import com.exiasoft.itscommon.util.ObjectUtil
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate

class LocalDateDeserializer : StdDeserializer<LocalDate>(LocalDate::class.java) {

    companion object {
        const val DATETIME_FORMAT =  "yyyy-MM-dd"
    }

    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): LocalDate? {
        val text = ObjectUtil.nvl(p?.text, "")
        return if (text.isBlank()) null else {
            val sdf = SimpleDateFormat(JACKSON_DATE_PATTERN_SIMPLE_DATE_FORMAT)
            return (Timestamp(sdf.parse(text).time)).toLocalDateTime().toLocalDate()
        }
    }
}