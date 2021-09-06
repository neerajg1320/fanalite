package com.fanalite.rulesapp.viewmodels

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fanalite.rulesapp.models.RegexModel


class RegexViewModel: ViewModel() {
    private val regexList: MutableLiveData<List<RegexModel>> by lazy {
        MutableLiveData<List<RegexModel>>().also {
            loadRegex()
        }
    }

    fun getRegexList(): LiveData<List<RegexModel>>   {
        return regexList
    }

    fun loadRegex() {
        Handler().postDelayed({
            val regexModelList: List<RegexModel> = mutableListOf(
                RegexModel("a", "Java", "a"),
                RegexModel("b", "Java", "b")
            )

            regexList.value = regexModelList

        }, 500)
    }
}