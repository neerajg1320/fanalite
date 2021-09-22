package com.fanalite.rulesapp.repository

import android.util.Log
import com.fanalite.rulesapp.models.RegexModel
import com.fanalite.rulesapp.retrofitRegexResource.RegexResourceApi
import com.fanalite.rulesapp.retrofitRegexResource.RegexResourceService
import com.fanalite.rulesapp.view.TAG

class RegexResourceRepository {

    private var regexResourceApi: RegexResourceApi = RegexResourceService.create();

    suspend fun insertRule(rule:RegexModel) {
        val regexResponse = regexResourceApi.create(rule)
        val regexModel = regexResponse.body();
        Log.d(TAG, "regex stored id=${regexModel?.id}");
    }

    suspend fun getAllRules(): List<RegexModel> {
        var regexList:List<RegexModel>? = null;
        val response = regexResourceApi.getAll();
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