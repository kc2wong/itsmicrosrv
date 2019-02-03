package com.exiasoft.itsaccount.converter

import com.exiasoft.itsaccount.model.enumeration.TradingAccountStatus
import com.exiasoft.itscommon.converter.AbstractEnum2StringConverter

class TradingAccountStatusConverter : AbstractEnum2StringConverter<TradingAccountStatus>(TradingAccountStatus::class.java) {
}