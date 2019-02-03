package com.exiasoft.itscommon.lang

class ItsException(val errorCode: String, val errorParam: List<String>, val throwable: Throwable?) : Exception(errorCode, throwable) {

    constructor(errorCode: String) : this(errorCode, emptyList(), null)

    constructor(errorCode: String, errorParam: String) : this(errorCode, listOf(errorParam), null)

    constructor(errorCode: String, errorParam: List<String>) : this(errorCode, errorParam, null)

    constructor(errorCode: String, throwable: Throwable?) : this(errorCode, emptyList(), throwable)

    constructor(errorCode: String, errorParam: String, throwable: Throwable?) : this(errorCode, listOf(errorParam), throwable)

}