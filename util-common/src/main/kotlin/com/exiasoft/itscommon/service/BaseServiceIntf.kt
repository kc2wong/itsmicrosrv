package com.exiasoft.itscommon.service

import com.exiasoft.itscommon.authen.AuthenticationToken
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

/**
 * Interface to define standard enquiry functions for following cases
 * 1. Objects without children
 * 2. Objects with children and want retrieve the complete objects.  In case when want to retrieve the top level object only, use {@link com.exiasoft.itscommon.service.BaseSimpleObjectServiceIntf}
 *
 * @param <K> the type of identifier
 * @param <I> the type of entity
 */
interface BaseServiceIntf<K, T> {

    fun findAll(authenToken: AuthenticationToken, pageable: Pageable): Mono<Page<T>>

    fun findByIdentifier(authenToken: AuthenticationToken, id: K): Mono<T>

    fun findByOid(authenToken: AuthenticationToken, oid: String): Mono<T>

    fun findByOids(authenToken: AuthenticationToken, oids: Set<String>): Mono<Map<String, T>>

}