package com.exiasoft.itscommon.resource

import com.exiasoft.itscommon.lang.ErrorCode
import com.exiasoft.itscommon.lang.ItsException
import com.exiasoft.itscommon.util.WebResponseUtil
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.ExceptionHandler


abstract class BaseResource {

    @ExceptionHandler(ItsException::class)
    fun handlerException(request: ServerHttpRequest, response: ServerHttpResponse, itse: ItsException) {
        response.headers[WebResponseUtil.RESP_HEADER_ERROR_CODE] = itse.errorCode
        if (itse.errorCode == ErrorCode.ERROR_CODE_NO_FUNC_ENTITLEMENT) {
            response.statusCode = HttpStatus.UNAUTHORIZED
        } else {
            response.statusCode = HttpStatus.BAD_REQUEST
        }
        if (itse.errorParam.isNotEmpty()) {
            response.headers[WebResponseUtil.RESP_HEADER_ERROR_PARAM] = itse.errorParam.joinToString()
        }
    }

}