package com.exiasoft.itscommon.converter

import com.exiasoft.itscommon.constant.STR_N
import com.exiasoft.itscommon.constant.STR_Y
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter

class BooleanConverter : AbstractSingleValueConverter() {

    override fun fromString(str: String?): Any {
        str?.let {
            return when (it) {
                STR_Y -> true
                STR_N -> false
                else -> false
            }
        }
        return false
    }

    override fun canConvert(type: Class<*>?): Boolean {
        return type == Boolean::class.java
    }

    override fun toString(obj: Any?): String? {
        return obj?.let {
            if (it as Boolean) STR_Y else STR_N
        }
    }
}