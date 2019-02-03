package com.exiasoft.itscommon.util

import com.exiasoft.itscommon.model.PagingSearchResult
import org.springframework.data.domain.Page
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class WebResponseUtil {

    companion object {

        const val ERROR_CODE_SYSTEM_ERROR = "SYSERR_09999"

        const val RESP_HEADER_ERROR_CODE = "X-itsrest-errorCode"
        const val RESP_HEADER_ERROR_PARAM = "X-itsrest-errorParam"

        fun <T> generatePaginationResponse(page: Page<*>, content: List<T>): PagingSearchResult<T> {
            return PagingSearchResult(page, content)
        }

        fun <T> generatePaginationResponseEntity(baseUrl: String, page: Mono<Page<T>>): Mono<ResponseEntity<PagingSearchResult<T>>> {
            return page.map { p ->
                val headers = generatePaginationHttpHeaders(p, baseUrl)
                ResponseEntity(PagingSearchResult(p, p.content), headers, HttpStatus.OK)
            }
        }

        fun <T, D> generatePaginationResponseEntity(baseUrl: String, page: Mono<Page<T>>, mapper: (List<T>) -> Flux<D>): Mono<ResponseEntity<PagingSearchResult<D>>> {
            return generatePaginationResponseEntity(baseUrl, page, emptyMap<String, Any>(), mapper)
        }

        fun <T, D> generatePaginationResponseEntity(baseUrl: String, page: Mono<Page<T>>, extraContent: Map<String, *>, mapper: (List<T>) -> Flux<D>): Mono<ResponseEntity<PagingSearchResult<D>>> {
            return page.flatMap { p ->
                val headers = generatePaginationHttpHeaders(p, baseUrl)
                mapper(p.content).collectList().map {
                    ResponseEntity(PagingSearchResult(p, it.asSequence().toList(), extraContent), headers, HttpStatus.OK)
                }
            }
        }

        fun <T, D> generatePaginationResponseEntity(baseUrl: String, page: Page<T>, mapper: (List<T>) -> Flux<D>): Mono<ResponseEntity<PagingSearchResult<D>>> {
            return generatePaginationResponseEntity(baseUrl, page, emptyMap<String, Any>(), mapper)
        }

        fun <T, D> generatePaginationResponseEntity(baseUrl: String, page: Page<T>, extraContent: Map<String, *>, mapper: (List<T>) -> Flux<D>): Mono<ResponseEntity<PagingSearchResult<D>>> {
            val headers = generatePaginationHttpHeaders(page, baseUrl)
            return mapper(page.content).collectList().flatMap {
                Mono.just(ResponseEntity(PagingSearchResult(page, it.asSequence().toList(), extraContent), headers, HttpStatus.OK))
            }
        }

        fun <T> generateFailureResponse(errorCode: String?, errorParam: List<String>? = null): Mono<ResponseEntity<T>> {
            val builder = ResponseEntity.badRequest()
                    .header(RESP_HEADER_ERROR_CODE, errorCode ?: ERROR_CODE_SYSTEM_ERROR)
            if (errorParam != null) {
                builder.header(RESP_HEADER_ERROR_PARAM, errorCode ?: errorParam.joinToString())
            }
            return Mono.just(builder.build())
        }

        fun <T> notFound(): Mono<ResponseEntity<T>> {
            return Mono.just(ResponseEntity(HttpStatus.NOT_FOUND))
        }

        fun <T> wrapOrNotFound(response: Mono<T>): Mono<ResponseEntity<T>> {
            return wrapOrNotFound(response, HttpHeaders())
        }

        fun <T> wrapOrNotFound(response: Mono<T>, headers: HttpHeaders): Mono<ResponseEntity<T>> {
            return response.map {
                ResponseEntity.ok().headers(headers).body(it)
            }.switchIfEmpty(notFound())
        }

        private fun generatePaginationHttpHeaders(page: Page<*>, baseUrl: String): HttpHeaders {
            val headers = HttpHeaders()
            headers.add("X-Total-Count", page.totalElements.toString())
            var link = ""
            if (page.getNumber() + 1 < page.getTotalPages()) {
                link = "<" + generateUri(baseUrl, page.getNumber() + 1, page.size) + ">; rel=\"next\","
            }
            // prev link
            if (page.getNumber() > 0) {
                link += "<" + generateUri(baseUrl, page.getNumber() - 1, page.size) + ">; rel=\"prev\","
            }
            // last and first link
            var lastPage = 0
            if (page.getTotalPages() > 0) {
                lastPage = page.getTotalPages() - 1
            }
            link += "<" + generateUri(baseUrl, lastPage, page.size) + ">; rel=\"last\","
            link += "<" + generateUri(baseUrl, 0, page.size) + ">; rel=\"first\""
            headers.add(HttpHeaders.LINK, link)
            return headers
        }

        private fun generateUri(baseUrl: String, page: Int, size: Int): String {
            return UriComponentsBuilder.fromUriString(baseUrl).queryParam("page", page).queryParam("size", size).toUriString()
        }
    }
}