package com.exiasoft.itsorder.model.enumeration

import com.fasterxml.jackson.annotation.JsonValue

enum class BuySell(val value: String) {
    Buy("B"), Sell("S");

    @JsonValue
    override fun toString(): String {
        return this.value
    }
}