package com.exiasoft.itscommon.converter

import com.exiasoft.itscommon.constant.XSTREAM_DATETIME_PATTERN_SIMPLE_DATE_FORMAT
import com.exiasoft.itscommon.constant.XSTREAM_DATETIME_PATTERN_STRING_FORMAT
import com.exiasoft.itscommon.util.ObjectUtil
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime

class LocalDateTimeConverter : AbstractSingleValueConverter() {

    override fun fromString(str: String?): Any? {
        val text = ObjectUtil.nvl(str, "")
        return if (text.isBlank()) null else {
            val sdf = SimpleDateFormat(XSTREAM_DATETIME_PATTERN_SIMPLE_DATE_FORMAT)
            (Timestamp(sdf.parse(text).time)).toLocalDateTime()
        }
    }

    override fun canConvert(type: Class<*>?): Boolean {
        return type == LocalDateTime::class.java
    }

    override fun toString(obj: Any?): String? {
        return obj ?.let {
            val localDateTime = it as LocalDateTime
            String.format(XSTREAM_DATETIME_PATTERN_STRING_FORMAT, localDateTime.year, localDateTime.monthValue, localDateTime.dayOfMonth,
                    localDateTime.hour, localDateTime.minute, localDateTime.second)
        }
    }
}