package com.exiasoft.itscommon.service.client

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.HEADER_AUTHORIZATION
import com.exiasoft.itscommon.model.PagingSearchResult
import com.exiasoft.itscommon.service.BaseServiceIntf
import mu.KotlinLogging
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class BaseClientServiceImpl<K, T>(val appName: String, val baseUri: String, val loadBalancingClient: LoadBalancerClient, val webClientBuilder: WebClient.Builder) : BaseServiceIntf<K, T> {

    val logger = KotlinLogging.logger {}

    private val typeClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1]

    abstract fun addQueryParameterForFindByIdentifier(uriBuilder: UriBuilder, id: K)

    fun addQueryParameter(uriBuilder: UriBuilder, name: String, value: String?) {
        value?.let { uriBuilder.queryParam(name, it) }
    }

    fun addQueryParameter(uriBuilder: UriBuilder, name: String, value: Set<String>?) {
        value?.let { uriBuilder.queryParam(name, "["+ it.joinToString(separator = ",") + "]") }
    }

    fun find(authenToken: AuthenticationToken, pageable: Pageable, queryParameterHandler: (uriBuilder: UriBuilder) -> Unit ): Mono<Page<T>> {
        val client = loadBalancingClient.choose(appName)
        val typeRef = object : ParameterizedTypeReference<PagingSearchResult<T>>() {
            override fun getType(): Type {
                return object: ParameterizedType {
                    override fun getRawType(): Type {
                        return PagingSearchResult::class.java
                    }

                    override fun getOwnerType(): Type {
                        return PagingSearchResult::class.java.declaringClass
                    }

                    override fun getActualTypeArguments(): Array<Type> {
                        return arrayOf(typeClass)
                    }
                }
            }
        }

        return webClientBuilder.build().get().uri {
            it.scheme(client.uri.scheme).host(client.uri.host).port(client.uri.port).path(baseUri)
            queryParameterHandler(it)
            it.build()
        }.accept(MediaType.APPLICATION_JSON_UTF8)
                // Quick solution is to set token to header, It is better to add webfilter to store all headers and pass to downstream
                .header(HEADER_AUTHORIZATION, authenToken.toString())
                .retrieve()
                .bodyToMono(typeRef).map { PageImpl(it.content, pageable, it.content.size.toLong()) }
    }

    override fun findAll(authenToken: AuthenticationToken, pageable: Pageable): Mono<Page<T>> {
        val client = loadBalancingClient.choose(appName)
        val typeRef = pagingSearchResultTypeRef()

        return webClientBuilder.build().get().uri {
            it.scheme(client.uri.scheme).host(client.uri.host).port(client.uri.port).path(baseUri).build()
        }.accept(MediaType.APPLICATION_JSON_UTF8)
                // Quick solution is to set token to header, It is better to add webfilter to store all headers and pass to downstream
                .header(HEADER_AUTHORIZATION, authenToken.toString())
                .retrieve()
                .bodyToMono(typeRef)
                .map { PageImpl(it.content, pageable, it.content.size.toLong()) }
    }

    override fun findByIdentifier(authenToken: AuthenticationToken, id: K): Mono<T> {
        val client = loadBalancingClient.choose(appName)
        val typeRef = pagingSearchResultTypeRef()

        return webClientBuilder.build().get().uri {
            it.scheme(client.uri.scheme).host(client.uri.host).port(client.uri.port).path(baseUri)
            addQueryParameterForFindByIdentifier(it, id)
            it.build()
        }.accept(MediaType.APPLICATION_JSON_UTF8)
                // Quick solution is to set token to header, It is better to add webfilter to store all headers and pass to downstream
                .header(HEADER_AUTHORIZATION, authenToken.toString())
                .retrieve()
                .bodyToMono(typeRef)
                .flatMap {
                    if (it.content.isEmpty()) Mono.empty() else Mono.just(it.content.first()) }
    }

    override fun findByOid(authenToken: AuthenticationToken, oid: String): Mono<T> {
        val client = loadBalancingClient.choose(appName)

        val path = "$baseUri/$oid"
        return webClientBuilder.build().get().uri {
            it.scheme(client.uri.scheme).host(client.uri.host).port(client.uri.port).path(path).build()
        }.accept(MediaType.APPLICATION_JSON_UTF8)
                // Quick solution is to set token to header, It is better to add webfilter to store all headers and pass to downstream
                .header(HEADER_AUTHORIZATION, authenToken.toString())
                .retrieve()
                .bodyToMono(typeClass as Class<T>)
    }

    override fun findByOids(authenToken: AuthenticationToken, oids: Set<String>): Mono<Map<String, T>> {
        val client = loadBalancingClient.choose(appName)
        val typeRef = mapTypeRef()

        return webClientBuilder.build().post().uri {
            it.scheme(client.uri.scheme).host(client.uri.host).port(client.uri.port).path(baseUri).build()
        }.contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                // Quick solution is to set token to header, It is better to add webfilter to store all headers and pass to downstream
                .header(HEADER_AUTHORIZATION, authenToken.toString())
                .body(BodyInserters.fromObject(oids)).retrieve().bodyToMono(typeRef)
    }

    private fun pagingSearchResultTypeRef(): ParameterizedTypeReference<PagingSearchResult<T>> {
        return object : ParameterizedTypeReference<PagingSearchResult<T>>() {
            override fun getType(): Type {
                return object: ParameterizedType {
                    override fun getRawType(): Type {
                        return PagingSearchResult::class.java
                    }

                    override fun getOwnerType(): Type {
                        return PagingSearchResult::class.java.declaringClass
                    }

                    override fun getActualTypeArguments(): Array<Type> {
                        return arrayOf(typeClass)
                    }
                }
            }
        }
    }

    private fun mapTypeRef(): ParameterizedTypeReference<Map<String, T>> {
        return object : ParameterizedTypeReference<Map<String, T>>() {
            override fun getType(): Type {
                return object: ParameterizedType {
                    override fun getRawType(): Type {
                        return LinkedHashMap::class.java
                    }

                    override fun getOwnerType(): Type {
                        return LinkedHashMap::class.java.declaringClass
                    }

                    override fun getActualTypeArguments(): Array<Type> {
                        return arrayOf(String::class.java, typeClass)
                    }
                }
            }
        }
    }
}