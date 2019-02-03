package com.exiasoft.itsorder.converter

import com.exiasoft.itscommon.converter.AbstractEnum2StringConverter
import com.exiasoft.itsorder.model.enumeration.OrderAction

class OrderActionConverter : AbstractEnum2StringConverter<OrderAction>(OrderAction::class.java)