package com.fanalite.rulesapp.retrofitRegexValidate

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RegexValidateService {
    private const val HOST = "192.168.1.134"
    private const val JAVA_PORT = "8080"
    private const val PYTHON_PORT = "8090"
    private const val JAVA_BASE_URL = "http://${HOST}:${JAVA_PORT}"
    private const val PYTHON_BASE_URL = "http://${HOST}:${PYTHON_PORT}"

    fun getRegexJavaApi(): RegexValidateApi {
        return Retrofit.Builder()
            .baseUrl(JAVA_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RegexValidateApi::class.java)
    }

    fun getRegexPythonApi(): RegexValidateApi {
        return Retrofit.Builder()
            .baseUrl(PYTHON_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RegexValidateApi::class.java)
    }
}