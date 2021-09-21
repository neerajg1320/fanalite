package com.fanalite.rulesapp.retrofitRegex

import com.fanalite.rulesapp.retrofitRegex.models.RegexValidateRequest
import com.fanalite.rulesapp.retrofitRegex.models.RegexValidateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegexValidateApi {
    @POST("regex/validate")
    suspend fun validateRegex(@Body request: RegexValidateRequest): Response<RegexValidateResponse>
}