package com.fanalite.rulesapp.retrofitRegexResource

import com.fanalite.rulesapp.retrofitRegexValidate.RegexValidateApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RegexResourceService {
    private const val HOST = "192.168.1.103"
    private const val PORT = "3030"
    private const val BASE_URL = "http://${HOST}:${PORT}"

    fun getRegexResourceApi(): RegexResourceApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RegexResourceApi::class.java)
    }
}