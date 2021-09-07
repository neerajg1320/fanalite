package com.fanalite.rulesapp.api.models

data class RegexValidateResponse (
    val status: String,
    val result: RegexValidateResult?,
    val error: String?
)
