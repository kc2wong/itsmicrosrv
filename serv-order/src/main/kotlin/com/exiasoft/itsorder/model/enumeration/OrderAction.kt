package com.exiasoft.itsorder.model.enumeration

import com.fasterxml.jackson.annotation.JsonValue

enum class OrderAction(val value: String) {
    Simulate("SIM"), Add("ADD"), Cancel("CAN");

    @JsonValue
    override fun toString(): String {
        return this.value
    }
}