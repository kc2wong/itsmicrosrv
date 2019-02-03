package com.exiasoft.itsstaticdata.model

import com.exiasoft.itscommon.model.enumaration.Status
import java.time.LocalDateTime

interface SimpleOperationUnit {

    var operationUnitOid: String

    var operationUnitCode: String

    var nameOneDefLang: String

    var nameTwoDefLang: String?

    var nameOne2ndLang: String?

    var nameTwo2ndLang: String?

    var nameOne3rdLang: String?

    var nameTwo3rdLang: String?

    var shortNameDefLang: String

    var shortName2ndLang: String?

    var shortName3rdLang: String?

    var demoIndicator: Boolean

    var lastModUser: String

    var lastModTimestamp: LocalDateTime

    var syncstr: String

    var status: Status

    var version: Int
}