package com.exiasoft.itscommon.serializer

import com.exiasoft.itscommon.model.enumaration.Status

class StatusDeserializer : AbstractEnum2StringDeserializer<Status>(Status::class.java) {
}