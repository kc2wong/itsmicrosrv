package com.exiasoft.itscommon.service

import com.exiasoft.itscommon.authen.AuthenticationToken
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

/**
 * Interface to define standard enquiry function for simple object.  No maintenance function should be defined
 *
 * @param <K> the type of identifier
 * @param <I> the type of simple object (Expect to be an interface)
 *
 */
interface BaseSimpleObjectServiceIntf<K, I> {

    fun findAll(authenToken: AuthenticationToken, pageable: Pageable): Mono<Page<I>>

    fun findByIdentifier(authenToken: AuthenticationToken, id: K): Mono<I>

    fun findByOid(authenToken: AuthenticationToken, oid: String): Mono<I>

    fun findByOids(authenToken: AuthenticationToken, oids: Set<String>): Mono<Map<String, I>>

}