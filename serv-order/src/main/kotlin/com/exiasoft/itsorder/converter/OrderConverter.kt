package com.exiasoft.itsorder.converter

import com.exiasoft.itsorder.model.Order
import com.thoughtworks.xstream.converters.UnmarshallingContext
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider
import com.thoughtworks.xstream.io.HierarchicalStreamReader
import com.thoughtworks.xstream.mapper.Mapper

class OrderConverter(mapper: Mapper, reflectionProvider: ReflectionProvider) : ReflectionConverter(mapper, reflectionProvider) {

    override fun canConvert(type: Class<*>?): Boolean {
        return type == Order::class.java
    }

    override fun unmarshal(reader: HierarchicalStreamReader?, context: UnmarshallingContext?): Any {

        val rtn = super.unmarshal(reader, context)
        val order = (rtn as Order)
        if (order.orderNumber == null) order.orderNumber = ""
        if (order.netAmount == null) order.netAmount = order.netConsideration
        if (order.orderExecution == null) order.orderExecution = emptyList()
        return rtn
    }
}