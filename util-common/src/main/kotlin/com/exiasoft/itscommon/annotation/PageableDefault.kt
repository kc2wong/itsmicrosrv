package com.exiasoft.itscommon.annotation

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@kotlin.annotation.Target(AnnotationTarget.VALUE_PARAMETER)
annotation class PageableDefault(
        /**
         * The default-size the injected [org.springframework.data.domain.Pageable] should get if no corresponding
         * parameter defined in request (default is -1, negative value means using system-wise defined by the spring property its.page.size).
         */
        val size: Int = -1,
        /**
         * The default-pagenumber the injected [org.springframework.data.domain.Pageable] should get if no corresponding
         * parameter defined in request (default is 0).
         */
        val page: Int = 0,
        /**
         * The properties and order to sort by by default. If unset, no sorting will be applied at all.  Each element is in formation propertyName,direction, e.g "code,asc"
         *
         * @return
         */
        val sort: Array<String> = arrayOf()
)