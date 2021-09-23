package com.fanalite.rulesapp.repository

import android.util.Log
import com.fanalite.rulesapp.retrofitFanalite.AuthenticationApi
import com.fanalite.rulesapp.retrofitFanalite.RegexResourceService
import com.fanalite.rulesapp.retrofitFanalite.models.CreateUserRequest
import com.fanalite.rulesapp.retrofitFanalite.models.LoginUserRequest
import com.fanalite.rulesapp.retrofitFanalite.models.LoginUserResponse
import com.fanalite.rulesapp.retrofitFanalite.models.User
import com.fanalite.rulesapp.view.TAG
import retrofit2.Response

class FanaliteAuthRepository {
    private val authApi: AuthenticationApi = RegexResourceService.createAuthenticationService()
    private val authStrategy = "local"

    suspend fun createUser(email:String, password:String): User? {
        val request = CreateUserRequest(email, password)
        val createResponse: Response<User> = authApi.createUser(request)
        return createResponse.body()
    }

    // This needs to be fixed with proper error handling
    // Just print is not enough
    suspend fun loginUser(email:String, password:String): LoginUserResponse? {
        val request = LoginUserRequest(email, password, authStrategy)
        val response: Response<LoginUserResponse> = authApi.loginUser(request)

        if (response.isSuccessful) {
            return response.body()
        } else {
            Log.e(TAG, "loginUser: ${response.code()}:${response.message()}");
        }

        return null
    }

}