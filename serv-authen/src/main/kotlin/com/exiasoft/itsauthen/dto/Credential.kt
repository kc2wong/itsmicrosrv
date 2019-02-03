package com.exiasoft.itsauthen.dto

data class Credential(val userid: String, val password: String, val rememberMe: Boolean = false)