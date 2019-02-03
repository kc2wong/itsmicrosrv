package com.exiasoft.itsstaticdata.annotation

import com.exiasoft.itsstaticdata.config.ItsStaticDataServiceServerConfig
import org.springframework.context.annotation.Import

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE)
@Import(ItsStaticDataServiceServerConfig::class)
annotation class EnableStaticDataServer {
}