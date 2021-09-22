package com.fanalite.rulesapp.retrofitFanalite

import com.fanalite.rulesapp.retrofitFanalite.models.CreateUserRequest
import com.fanalite.rulesapp.retrofitFanalite.models.LoginUserRequest
import com.fanalite.rulesapp.retrofitFanalite.models.LoginUserResponse
import com.fanalite.rulesapp.retrofitFanalite.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApi {
    @POST("users")
    suspend fun createUser(@Body request:CreateUserRequest): Response<User>

    @POST("authentication")
    suspend fun loginUser(@Body request:LoginUserRequest): Response<LoginUserResponse>
}