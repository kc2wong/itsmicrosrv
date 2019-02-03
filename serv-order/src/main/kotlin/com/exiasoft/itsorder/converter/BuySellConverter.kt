package com.exiasoft.itsorder.converter

import com.exiasoft.itscommon.converter.AbstractEnum2StringConverter
import com.exiasoft.itsorder.model.enumeration.BuySell

class BuySellConverter : AbstractEnum2StringConverter<BuySell>(BuySell::class.java)