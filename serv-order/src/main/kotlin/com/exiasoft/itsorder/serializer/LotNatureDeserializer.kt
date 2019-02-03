package com.exiasoft.itsorder.serializer

import com.exiasoft.itscommon.serializer.AbstractEnum2StringDeserializer
import com.exiasoft.itsorder.model.enumeration.LotNature

class LotNatureDeserializer : AbstractEnum2StringDeserializer<LotNature>(LotNature::class.java)
