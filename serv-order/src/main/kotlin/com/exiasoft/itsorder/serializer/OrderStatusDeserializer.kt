package com.exiasoft.itsorder.serializer

import com.exiasoft.itscommon.serializer.AbstractEnum2StringDeserializer
import com.exiasoft.itsorder.model.enumeration.OrderStatus

class OrderStatusDeserializer : AbstractEnum2StringDeserializer<OrderStatus>(OrderStatus::class.java)
