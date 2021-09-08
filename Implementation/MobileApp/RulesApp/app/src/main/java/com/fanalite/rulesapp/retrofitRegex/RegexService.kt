package com.fanalite.rulesapp.retrofitRegex

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RegexService {
    private const val HOST = "192.168.1.134"
    private const val JAVA_PORT = "8080"
    private const val PYTHON_PORT = "8090"
    private const val JAVA_BASE_URL = "http://${HOST}:${JAVA_PORT}"
    private const val PYTHON_BASE_URL = "http://${HOST}:${PYTHON_PORT}"

    fun getRegexJavaApi(): RegexApi {
        return Retrofit.Builder()
            .baseUrl(JAVA_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RegexApi::class.java)
    }

    fun getRegexPythonApi(): RegexApi {
        return Retrofit.Builder()
            .baseUrl(PYTHON_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RegexApi::class.java)
    }
}