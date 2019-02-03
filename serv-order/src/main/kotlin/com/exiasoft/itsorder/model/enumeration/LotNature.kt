package com.exiasoft.itsorder.model.enumeration

import com.fasterxml.jackson.annotation.JsonValue

enum class LotNature(val value: String) {
    Board("BRD"), Odd("ODD");

    @JsonValue
    override fun toString(): String {
        return this.value
    }
}