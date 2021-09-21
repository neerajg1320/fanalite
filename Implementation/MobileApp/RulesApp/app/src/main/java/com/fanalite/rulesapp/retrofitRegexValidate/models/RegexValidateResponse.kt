package com.fanalite.rulesapp.retrofitRegexValidate.models

data class RegexValidateResponse (
    val status: String,
    val result: RegexValidateResult?,
    val error: String?
)
