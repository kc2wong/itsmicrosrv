package com.exiasoft.itscommon.converter

import com.exiasoft.itscommon.model.enumaration.Status

class StatusConverter : AbstractEnum2StringConverter<Status>(Status::class.java) {
}