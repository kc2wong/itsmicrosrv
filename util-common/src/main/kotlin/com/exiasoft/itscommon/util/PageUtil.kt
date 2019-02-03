package com.exiasoft.itscommon.util

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

class PageUtil {
    companion object {
        private val unlimitedPage = PageRequest.of(0, Int.MAX_VALUE)

        fun unlimit(): Pageable {
            return unlimitedPage
        }

        fun <T> monoFromFirstOrEmpty(page: Page<T>): Mono<T> {
            return if (page.isEmpty) Mono.empty() else Mono.just(page.content.first())
        }

    }
}