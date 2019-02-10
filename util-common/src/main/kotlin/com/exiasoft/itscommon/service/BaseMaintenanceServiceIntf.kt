package com.exiasoft.itscommon.service

import com.exiasoft.itscommon.authen.AuthenticationToken
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

/**
 * Interface to define standard maintenance functions
 *
 * @param <K> the type of identifier
 * @param <I> the type of entity
 */
interface BaseMaintenanceServiceIntf<T> {

    fun insert(authenToken: AuthenticationToken, obj: T)

    fun update(authenToken: AuthenticationToken, obj: T)

    fun delete(authenToken: AuthenticationToken, obj: T)

}