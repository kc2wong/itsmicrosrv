package com.exiasoft.itsorder.model.enumeration

import com.fasterxml.jackson.annotation.JsonValue

enum class OrderStatus(val value: String) {
    Pending("PDN"), New("NEW"), Reserved("RD"), WaitForApprove("WA"), Rejected("REJ"),
    Cancelled("CAN"), Queued("Q"), PartialExecuted("PEX"), FullyExecuted("FEX");

    @JsonValue
    override fun toString(): String {
        return this.value
    }
}