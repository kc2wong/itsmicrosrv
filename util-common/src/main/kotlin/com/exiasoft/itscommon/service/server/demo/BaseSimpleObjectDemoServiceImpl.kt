package com.exiasoft.itscommon.service.server.demo


import com.exiasoft.itscommon.annotation.ItsFunction
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.bean.XStreamProvider
import com.exiasoft.itscommon.lang.ErrorCode
import com.exiasoft.itscommon.lang.ItsException
import com.exiasoft.itscommon.service.BaseServiceIntf
import com.exiasoft.itscommon.service.BaseSimpleObjectServiceIntf
import com.exiasoft.itscommon.service.msg.ItsMsg
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import reactor.core.publisher.Mono
import java.lang.reflect.ParameterizedType
import java.util.*

abstract class BaseSimpleObjectDemoServiceImpl<K, I, T : I>(val tokenProvider: TokenProvider, val xStreamProvider: XStreamProvider, clazz: Class<T>, additionXStreamClass: List<Class<*>> = emptyList()): BaseSimpleObjectServiceIntf<K, I> {

    private val objectMapper = ObjectMapper()

    var data: List<I>

    var itsFunctionMap: Map<String, ItsFunction>

    abstract fun getResourceName(): String

    abstract fun isEqualsInIdentifier(id: K, obj: I): Boolean

    abstract fun getOid(obj: I): String

    init {
        objectMapper.registerKotlinModule()
        val xStream = xStreamProvider.provideXstreamForSimpleObject()
        xStream.processAnnotations(clazz)
        additionXStreamClass.forEach { xStream.processAnnotations(it) }
        val inStream = this.javaClass.getResource(getResourceName())
        val itsMsg = xStream.fromXML(inStream) as ItsMsg<T>
        data = itsMsg.body.doData.content.map { it as I }.toList()
        itsFunctionMap = javaClass.declaredMethods.filter { it.getAnnotation(ItsFunction::class.java) != null }
                .map { it.name to it.getAnnotation(ItsFunction::class.java) }
                .toMap()
    }

    override fun findAll(authenToken: AuthenticationToken, pageable: Pageable): Mono<Page<I>> {
        getItsFunctionCode(authenToken)
        val result = if (pageable != Pageable.unpaged()) data.sortedWith(createComparator(pageable)) else data
        return Mono.just(getPage(authenToken, result, pageable))
    }

    override fun findByIdentifier(authenToken: AuthenticationToken, id: K): Mono<I> {
        getItsFunctionCode(authenToken)
        return data.find { isEqualsInIdentifier(id, it) }?.let { Mono.just(it) } ?: Mono.empty()
    }

    override fun findByOid(authenToken: AuthenticationToken, oid: String): Mono<I> {
        getItsFunctionCode(authenToken)
        return data.find { getOid(it) == oid }?.let { Mono.just(it) } ?: Mono.empty()
    }

    override fun findByOids(authenToken: AuthenticationToken, oids: Set<String>): Mono<Map<String, I>> {
        getItsFunctionCode(authenToken)
        return Mono.just(data.filter { oids.contains(getOid(it)) }.map { getOid(it) to it }.toMap())
    }

    fun createComparator(pageable: Pageable) : Comparator<I> {
        return Comparator { o1, o2 ->
            val json1 = objectMapper.convertValue(o1, Map::class.java)
            val json2 = objectMapper.convertValue(o2, Map::class.java)
            var result = 0
            for (s in pageable.sort) {
                val v1 = json1[s.property] as String
                val v2 = json2[s.property] as String
                result = if (s.direction == Sort.Direction.ASC) v1.compareTo(v2) else v2.compareTo(v1)
                if (result != 0) {
                    break
                }
            }
            result
        }
    }

    fun getPage(authenToken: AuthenticationToken, result: List<I>, pageable: Pageable): Page<I> {
        getItsFunctionCode(authenToken)
        if (pageable == Pageable.unpaged()) {
            return PageImpl(result, pageable, result.size.toLong())
        }
        else {
            val startIdx = Math.min(pageable.pageNumber * pageable.pageSize, result.size)
            val endIdx = Math.min((pageable.pageNumber + 1) * pageable.pageSize, result.size)
            return PageImpl(result.subList(startIdx, endIdx), pageable, result.size.toLong())
        }
    }

    private fun getItsFunctionCode(authenToken: AuthenticationToken) : String {
        val itsFunction = Thread.currentThread().stackTrace
                .filter { it.className.equals(this.javaClass.name) && itsFunctionMap.containsKey(it.methodName) }
                .firstOrNull()?.let {
                    itsFunctionMap.get(it.methodName)
                } ?: javaClass.getAnnotation(ItsFunction::class.java)
        if (itsFunction == null || itsFunction.code.isEmpty()) {
            throw ItsException(ErrorCode.ERROR_CODE_FUNCTION_CODE_MISSING)
        }
         return authenToken.getAuthentication()?.let {
            val entitlement = it.authorities.map { a -> a.authority }.toSet()
             val match = itsFunction.code.find { f -> entitlement.contains(f) }
             return match ?: throw ItsException(ErrorCode.ERROR_CODE_NO_FUNC_ENTITLEMENT, itsFunction.code[0])
        } ?: itsFunction.code[0]
    }

}