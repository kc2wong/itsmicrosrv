package com.exiasoft.itsorder.converter

import com.exiasoft.itscommon.converter.AbstractEnum2StringConverter
import com.exiasoft.itsorder.model.enumeration.LotNature

class LotNatureConverter : AbstractEnum2StringConverter<LotNature>(LotNature::class.java)