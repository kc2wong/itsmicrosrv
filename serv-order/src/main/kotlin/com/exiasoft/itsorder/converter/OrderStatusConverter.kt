package com.exiasoft.itsorder.converter

import com.exiasoft.itscommon.converter.AbstractEnum2StringConverter
import com.exiasoft.itsorder.model.enumeration.OrderStatus

class OrderStatusConverter : AbstractEnum2StringConverter<OrderStatus>(OrderStatus::class.java)