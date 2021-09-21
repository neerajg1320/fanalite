package com.fanalite.rulesapp.retrofitRegexValidate

import com.fanalite.rulesapp.retrofitRegexValidate.models.RegexValidateRequest
import com.fanalite.rulesapp.retrofitRegexValidate.models.RegexValidateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegexValidateApi {
    @POST("regex/validate")
    suspend fun validateRegex(@Body request: RegexValidateRequest): Response<RegexValidateResponse>
}