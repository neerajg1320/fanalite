package com.fanalite.rulesapp.api

import com.fanalite.rulesapp.api.models.RegexValidateRequest
import com.fanalite.rulesapp.api.models.RegexValidateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegexApi {
    @POST("regex/validate")
    suspend fun validateRegex(@Body request: RegexValidateRequest): Response<RegexValidateResponse>
}