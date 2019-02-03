package com.exiasoft.itscommon.lang

class ErrorCode {
    companion object {
        // 80001 to 89999 - Error from related to ITSRestkt
        // 90001 to 99999 - Error from related to ITS server process result
        const val ERROR_CODE_FUNCTION_CODE_MISSING = "SYSERR.00201E"

        const val ERROR_CODE_NO_FUNC_ENTITLEMENT = "SYSERR.89997E"      // No function entitlement
        const val ERROR_CODE_UNSUPPORTED_OPERATION = "SYSERR.89998E"           // Unsupported Operation %s
        const val ERROR_CODE_MISSING_ALIAS_NAME = "SYSERR.89999E"           // Missing alias name in class %s

        const val ERROR_CODE_CONNECTION_FAIL = "SYSERR.99997E"           // Fail to connect to ITS server at %s
        const val ERROR_CODE_UNKNOWN_RESULT = "SYSERR.99998E"           // ITS5 process result without success or fail
        const val ERROR_CODE_SYSTEM_ERROR = "SYSERR.99999E"             // ITS5 process result fail but without error code
    }
}