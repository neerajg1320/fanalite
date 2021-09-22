package com.fanalite.rulesapp.retrofitRegexResource

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RegexResourceService {
    private const val HOST = "192.168.1.103"
    private const val PORT = "3030"
    private const val BASE_URL = "http://${HOST}:${PORT}"

    private const val AUTH_TOKEN_TYPE = "Bearer"
    private const val AUTH_ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6ImFjY2VzcyJ9.eyJpYXQiOjE2MzIyOTExMDQsImV4cCI6MTYzMjM3NzUwNCwiYXVkIjoiaHR0cHM6Ly95b3VyZG9tYWluLmNvbSIsImlzcyI6ImZlYXRoZXJzIiwic3ViIjoiRFdET3kxY3dKbHc4SFB3OCIsImp0aSI6ImM3Y2FhZDI1LTA1NjctNGEyZC1iMTVjLWVhZjJjNGQyZDkxYiJ9.Ara_PrUvPalfJcckab5jPT6PBvR0J7dI3PE7lMg0TcM"

    // We have kept the creation of dependencies in the function so that in future we can create
    // connection to multiple API endpoints using this same service
    fun create(): RegexResourceApi {
        val authClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(AUTH_TOKEN_TYPE, AUTH_ACCESS_TOKEN))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(authClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        return retrofit.create(RegexResourceApi::class.java)
    }
}