package com.exiasoft.itsaccount.config

import com.exiasoft.itsauthen.config.FunctionListConfig
import com.exiasoft.itsauthen.config.ItsAuthenServiceClientAutoConfig
import com.exiasoft.itsauthen.service.impl.JwtTokenProvider
import com.exiasoft.itscommon.config.WebFluxConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@ComponentScan("com.exiasoft.itsaccount")
@Import(value = [WebFluxConfiguration::class, ItsAuthenServiceClientAutoConfig::class, FunctionListConfig::class, JwtTokenProvider::class])
class ItsAccountTestConfig