package com.exiasoft.itscommon.util

class ObjectUtil {
    companion object {
        fun <T> nvl(value: T?, defaultValue: T): T {
            return value ?: defaultValue
        }
    }
}