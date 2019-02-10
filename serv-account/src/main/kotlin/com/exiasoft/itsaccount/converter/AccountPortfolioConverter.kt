package com.exiasoft.itsaccount.converter

import com.exiasoft.itsaccount.model.AccountPortfolio
import com.thoughtworks.xstream.converters.UnmarshallingContext
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider
import com.thoughtworks.xstream.io.HierarchicalStreamReader
import com.thoughtworks.xstream.mapper.Mapper

class AccountPortfolioConverter(mapper: Mapper, reflectionProvider: ReflectionProvider) : ReflectionConverter(mapper, reflectionProvider) {

    override fun canConvert(type: Class<*>?): Boolean {
        return type == AccountPortfolio::class.java
    }

    override fun unmarshal(reader: HierarchicalStreamReader?, context: UnmarshallingContext?): Any {
        val rtn = super.unmarshal(reader, context)
        val accountPortfolio = (rtn as AccountPortfolio)
        if (accountPortfolio.currencyCode == null) accountPortfolio.currencyCode = ""
        return rtn
    }
}