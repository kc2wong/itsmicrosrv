package com.exiasoft.itscommon.converter

import com.exiasoft.itscommon.constant.XSTREAM_DATE_PATTERN_SIMPLE_DATE_FORMAT
import com.exiasoft.itscommon.constant.XSTREAM_DATE_PATTERN_STRING_FORMAT
import com.exiasoft.itscommon.util.ObjectUtil
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate

class LocalDateConverter : AbstractSingleValueConverter() {

    override fun fromString(str: String?): Any? {
        val text = ObjectUtil.nvl(str, "")
        return if (text.isBlank()) null else {
            val sdf = SimpleDateFormat(XSTREAM_DATE_PATTERN_SIMPLE_DATE_FORMAT)
            (Timestamp(sdf.parse(text).time)).toLocalDateTime().toLocalDate()
        }
    }

    override fun canConvert(type: Class<*>?): Boolean {
        return type == LocalDate::class.java
    }

    override fun toString(obj: Any?): String? {
        return obj?.let {
            val localDate = obj as LocalDate
            String.format(XSTREAM_DATE_PATTERN_STRING_FORMAT, localDate.year, localDate.monthValue, localDate.dayOfMonth)
        }
    }

}