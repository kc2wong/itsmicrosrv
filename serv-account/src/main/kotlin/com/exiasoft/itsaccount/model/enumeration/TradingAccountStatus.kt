package com.exiasoft.itsaccount.model.enumeration

import com.fasterxml.jackson.annotation.JsonValue

enum class TradingAccountStatus(val value: String) {
    Active("A"), Inactive("I");

    @JsonValue
    override fun toString(): String {
        return this.value
    }
}