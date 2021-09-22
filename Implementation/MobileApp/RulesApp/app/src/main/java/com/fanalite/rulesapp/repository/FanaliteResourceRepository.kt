package com.fanalite.rulesapp.repository

import android.util.Log
import com.fanalite.rulesapp.models.RegexModel
import com.fanalite.rulesapp.retrofitFanalite.RegexResourceApi
import com.fanalite.rulesapp.retrofitFanalite.RegexResourceService
import com.fanalite.rulesapp.view.TAG

class FanaliteResourceRepository(authTokenType:String, authAccessToken:String) {

    private var regexResourceApi: RegexResourceApi = RegexResourceService.createRegexResource(authTokenType, authAccessToken);

    suspend fun insertRule(rule:RegexModel) {
        val regexResponse = regexResourceApi.createRule(rule)
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
    suspend fun updateRule(id:String, rule:RegexModel) {
        val regexResponse = regexResourceApi.updateRule(id, rule)
    }

    suspend fun deleteRule(id:String) {
        val regexResponse = regexResourceApi.deleteRule(id)
        val regexModel = regexResponse.body();
        Log.d(TAG, "regex deleted id=${regexModel?.id}");
    }
}