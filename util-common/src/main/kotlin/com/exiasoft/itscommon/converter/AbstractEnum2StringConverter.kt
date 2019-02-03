package com.exiasoft.itscommon.converter

import com.exiasoft.itscommon.util.EnumUtil
import com.exiasoft.itscommon.util.ObjectUtil
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter

abstract  class AbstractEnum2StringConverter<T : Enum<T>>(private val enumClass: Class<T>) : AbstractSingleValueConverter() {

    override fun fromString(str: String?): Any? {
        val text = ObjectUtil.nvl(str, "")
        return if (text.isBlank()) null else EnumUtil.str2Enum(enumClass, text)
    }

    override fun canConvert(type: Class<*>?): Boolean {
        return type == enumClass
    }

    override fun toString(obj: Any?): String {
        return obj?.toString() ?: ""
    }
}
