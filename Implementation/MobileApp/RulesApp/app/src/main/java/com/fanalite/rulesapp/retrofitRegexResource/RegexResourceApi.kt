package com.fanalite.rulesapp.retrofitRegexResource

import com.fanalite.rulesapp.models.RegexModel
import com.fanalite.rulesapp.retrofitRegexResource.models.RegexGetAllResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RegexResourceApi {

    @POST("rules")
    suspend fun create(@Body request: RegexModel): Response<RegexModel>

    @GET("rules")
    suspend fun getAll(): Response<RegexGetAllResponse>
}