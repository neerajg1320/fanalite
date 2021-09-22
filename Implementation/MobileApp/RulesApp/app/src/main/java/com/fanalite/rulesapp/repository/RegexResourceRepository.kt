package com.fanalite.rulesapp.repository

import android.util.Log
import com.fanalite.rulesapp.models.RegexModel
import com.fanalite.rulesapp.retrofitRegexResource.RegexResourceApi
import com.fanalite.rulesapp.retrofitRegexResource.RegexResourceService
import com.fanalite.rulesapp.view.TAG
import java.net.ConnectException

class RegexResourceRepository {

    private var regexResourceApi: RegexResourceApi = RegexResourceService.getRegexResourceApi();

    private val accessToken:String = "eyJhbGciOiJIUzI1NiIsInR5cCI6ImFjY2VzcyJ9.eyJpYXQiOjE2MzIyOTExMDQsImV4cCI6MTYzMjM3NzUwNCwiYXVkIjoiaHR0cHM6Ly95b3VyZG9tYWluLmNvbSIsImlzcyI6ImZlYXRoZXJzIiwic3ViIjoiRFdET3kxY3dKbHc4SFB3OCIsImp0aSI6ImM3Y2FhZDI1LTA1NjctNGEyZC1iMTVjLWVhZjJjNGQyZDkxYiJ9.Ara_PrUvPalfJcckab5jPT6PBvR0J7dI3PE7lMg0TcM"

    suspend fun insertRule(rule:RegexModel) {
        val regexResponse = regexResourceApi.create(rule, accessToken)
        val regexModel = regexResponse.body();
        Log.d(TAG, "regex stored id=${regexModel?.id}");
    }

    suspend fun getAllRules(): List<RegexModel> {
        var regexList:List<RegexModel>? = null;
        val response = regexResourceApi.getAll(accessToken);
        if (response.isSuccessful) {
            val regexGetAllResponse = response.body();
            Log.d(TAG, "getAllRules: response: $response");
            if (regexGetAllResponse?.data != null) {
                regexList = regexGetAllResponse.data
            }
        } else {
            Log.e(TAG, "getAllRules: ${response.code()}:${response.message()}");
        }

        return regexList ?: emptyList();
    }
}