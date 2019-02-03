package com.exiasoft.itscommon.service

import com.exiasoft.itscommon.authen.AuthenticationToken
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

interface BaseServiceIntf<K, T> {

    fun findAll(authenToken: AuthenticationToken, pageable: Pageable): Mono<Page<T>>

    fun findByIdentifier(authenToken: AuthenticationToken, id: K): Mono<T>

    fun findByOid(authenToken: AuthenticationToken, oid: String): Mono<T>

    fun findByOids(authenToken: AuthenticationToken, oids: Set<String>): Mono<Map<String, T>>

}