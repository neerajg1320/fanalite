package com.fanalite.rulesapp.repository

import com.fanalite.rulesapp.retrofitFanalite.AuthenticationApi
import com.fanalite.rulesapp.retrofitFanalite.RegexResourceService
import com.fanalite.rulesapp.retrofitFanalite.models.CreateUserRequest
import com.fanalite.rulesapp.retrofitFanalite.models.LoginUserRequest
import com.fanalite.rulesapp.retrofitFanalite.models.LoginUserResponse
import com.fanalite.rulesapp.retrofitFanalite.models.User
import retrofit2.Response

class FanaliteAuthRepository {
    private val authApi: AuthenticationApi = RegexResourceService.createAuthenticationService()
    private val authStrategy = "local"

    suspend fun createUser(email:String, password:String): User? {
        val request = CreateUserRequest(email, password)
        val createResponse: Response<User> = authApi.createUser(request)
        return createResponse.body()
    }

    suspend fun loginUser(email:String, password:String): LoginUserResponse? {
        val request = LoginUserRequest(email, password, authStrategy)
        val createResponse: Response<LoginUserResponse> = authApi.loginUser(request)
        return createResponse.body()
    }

}