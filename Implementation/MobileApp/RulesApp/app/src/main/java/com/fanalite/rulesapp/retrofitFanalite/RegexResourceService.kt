package com.fanalite.rulesapp.retrofitFanalite

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RegexResourceService {
    private const val HOST = "192.168.1.103"
    private const val PORT = "3030"
    private const val BASE_URL = "http://${HOST}:${PORT}"

    // We have kept the creation of dependencies in the function so that in future we can create
    // connection to multiple API endpoints using this same service
    fun createRegexResource(authTokenType:String, authAccessToken:String): RegexResourceApi {
        val authClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(authTokenType, authAccessToken))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(authClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        return retrofit.create(RegexResourceApi::class.java)
    }

    fun createAuthenticationService(): AuthenticationApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        return retrofit.create(AuthenticationApi::class.java)
    }
}