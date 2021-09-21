package com.fanalite.rulesapp.repository

import android.util.Log
import com.fanalite.rulesapp.models.RegexModel
import com.fanalite.rulesapp.retrofitRegexResource.RegexResourceApi
import com.fanalite.rulesapp.retrofitRegexResource.RegexResourceService
import com.fanalite.rulesapp.view.TAG

class RegexResourceRepository {

    private var regexResourceApi: RegexResourceApi = RegexResourceService.getRegexResourceApi();

    suspend fun insertRule(rule:RegexModel) {
        val regexResponse = regexResourceApi.create(rule)
        val regexModel = regexResponse.body();
        Log.d(TAG, "regex stored id=${regexModel?.id}");
    }
}