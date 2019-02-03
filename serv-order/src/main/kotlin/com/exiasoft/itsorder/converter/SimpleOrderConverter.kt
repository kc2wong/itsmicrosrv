package com.exiasoft.itsorder.converter

import com.exiasoft.itsorder.model.SimpleOrderData
import com.thoughtworks.xstream.converters.UnmarshallingContext
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider
import com.thoughtworks.xstream.io.HierarchicalStreamReader
import com.thoughtworks.xstream.mapper.Mapper

class SimpleOrderConverter(mapper: Mapper, reflectionProvider: ReflectionProvider) : ReflectionConverter(mapper, reflectionProvider) {

    override fun canConvert(type: Class<*>?): Boolean {
        return type == SimpleOrderData::class.java
    }

    override fun unmarshal(reader: HierarchicalStreamReader?, context: UnmarshallingContext?): Any {
        val rtn = super.unmarshal(reader, context)
        val simpleOrder = (rtn as SimpleOrderData)
        if (simpleOrder.orderNumber == null) simpleOrder.orderNumber = ""
        if (simpleOrder.netAmount == null) simpleOrder.netAmount = simpleOrder.netConsideration
        return rtn
    }
}