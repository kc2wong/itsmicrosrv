package com.exiasoft.itscommon.model

import org.springframework.data.domain.Page

data class PagingSearchResult<T> (
        val currentPage: Int,
        val totalPage: Int,
        val pageSize: Int,
        val totalCount: Int?,
        val hasNext: Boolean,
        val content: List<T>,
        val extraContent: Map<String, *>?
) {
    constructor(page: Page<T>) : this(page.number, page.totalPages, page.size, page.totalElements.toInt(), page.hasNext(), page.content, null)
    constructor(page: Page<*>, content: List<T>) : this(page.number, page.totalPages, page.size, page.totalElements.toInt(), page.hasNext(), content, null)
    constructor(page: Page<*>, content: List<T>, extraContent: Map<String, *>) : this(page.number, page.totalPages, page.size, page.totalElements.toInt(), page.hasNext(), content, extraContent)
}