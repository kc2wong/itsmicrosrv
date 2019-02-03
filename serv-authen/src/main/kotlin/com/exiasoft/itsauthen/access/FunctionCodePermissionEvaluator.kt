package com.exiasoft.itsauthen.access

import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import java.io.Serializable

class FunctionCodePermissionEvaluator : PermissionEvaluator {

    fun hasPermission(authentication: Authentication?, functionCodeListStr: String): Boolean {
        val found = authentication?.let {auth ->
            val authorities = auth.authorities.map { a -> a.authority }
            val functionCodeList =  if (functionCodeListStr.length >= 2)
                functionCodeListStr.subSequence(1, functionCodeListStr.length - 1).splitToSequence(",").toList()
            else emptyList()
            functionCodeList.find { authorities.contains(it)  }
        }
        return found != null
    }

    override fun hasPermission(authentication: Authentication?, targetDomainObject: Any?, permission: Any?): Boolean {
        return targetDomainObject ?.let { hasPermission(authentication, it.toString()) } ?: false
    }

    override fun hasPermission(authentication: Authentication?, targetId: Serializable?, targetType: String?, permission: Any?): Boolean {
        return targetId ?.let { hasPermission(authentication, it.toString()) } ?: false
    }
}