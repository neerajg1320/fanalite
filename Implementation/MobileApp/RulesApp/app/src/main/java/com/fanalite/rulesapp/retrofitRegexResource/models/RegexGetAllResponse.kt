package com.fanalite.rulesapp.retrofitRegexResource.models

import com.fanalite.rulesapp.models.RegexModel

data class RegexGetAllResponse (
    val total: Int,
    val limit: Int,
    val skip: Int,
    val data: List<RegexModel>
)