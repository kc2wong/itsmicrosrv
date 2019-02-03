package com.exiasoft.itscommon.model.enumaration

import com.fasterxml.jackson.annotation.JsonValue

enum class Status(val value: String) {
    Active("A"), Inactive("I");

    @JsonValue
    override fun toString(): String {
        return this.value
    }
}