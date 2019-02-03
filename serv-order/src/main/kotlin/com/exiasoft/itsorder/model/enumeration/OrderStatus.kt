package com.exiasoft.itsorder.model.enumeration

enum class OrderStatus(val value: String) {
    Pending("PDN"), New("NEW"), Reserved("RD"), WaitForApprove("WA"), Rejected("REJ"),
    Cancelled("CAN"), Queued("Q"), PartialExecuted("PEX"), FullyExecuted("FEX");

    override fun toString(): String {
        return this.value
    }
}