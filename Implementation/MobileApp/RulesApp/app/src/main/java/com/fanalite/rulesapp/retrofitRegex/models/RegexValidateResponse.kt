package com.fanalite.rulesapp.retrofitRegex.models

data class RegexValidateResponse (
    val status: String,
    val result: RegexValidateResult?,
    val error: String?
)
