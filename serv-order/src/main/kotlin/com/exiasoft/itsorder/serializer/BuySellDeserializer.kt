package com.exiasoft.itsorder.serializer

import com.exiasoft.itscommon.serializer.AbstractEnum2StringDeserializer
import com.exiasoft.itsorder.model.enumeration.BuySell

class BuySellDeserializer : AbstractEnum2StringDeserializer<BuySell>(BuySell::class.java)
