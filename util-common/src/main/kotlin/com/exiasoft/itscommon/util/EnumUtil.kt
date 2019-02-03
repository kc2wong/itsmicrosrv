package com.exiasoft.itscommon.util

class EnumUtil {
    companion object {

        fun <T : Enum<T>> str2Enum(c: Class<T>, str: String?): T? {
            return str?.let {
                c.enumConstants.find{ v -> v.toString() == it}
            }
        }

        fun <T : Enum<T>> int2Enum(c: Class<T>, int: Int?): T? {
            return int?.let {
                c.enumConstants.find{ v -> v.toString().toInt() == it}
            }
        }
    }
}