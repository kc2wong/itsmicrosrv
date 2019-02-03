package com.exiasoft.itscommon.util

import java.util.*

fun String.base64StringToByteArray(): ByteArray {
    return Base64.getDecoder().decode(this)
}

fun ByteArray.toBase64String(): String {
    return String(Base64.getEncoder().encode(this))
}

fun <T> List<T>.firstOrDefault(default: T): T {
    return firstOrNull() ?: default
}
