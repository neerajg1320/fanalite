package com.fanalite.rulesapp.retrofitFanalite

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenType:String, private val accessToken: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .header("Authorization", "${tokenType} ${accessToken}")
            .build()
        return chain.proceed(request)
    }
}