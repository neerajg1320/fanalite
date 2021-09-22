package com.fanalite.rulesapp.retrofitFanalite

import com.fanalite.rulesapp.models.RegexModel
import com.fanalite.rulesapp.retrofitFanalite.models.RegexGetAllResponse
import retrofit2.Response
import retrofit2.http.*

interface RegexResourceApi {

    @POST("rules")
    suspend fun createRule(@Body request: RegexModel): Response<RegexModel>

    @GET("rules")
    suspend fun getAll(): Response<RegexGetAllResponse>

    @PUT("rules/{rule_id}")
    suspend fun updateRule(@Path(value="rule_id") rule_id:String, @Body request: RegexModel): Response<RegexModel>

    @DELETE("rules/{rule_id}")
    suspend fun deleteRule(@Path(value="rule_id") rule_id:String): Response<RegexModel>
}