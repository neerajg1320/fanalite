package com.fanalite.rulesapp.viewmodels

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fanalite.rulesapp.models.RegexModel


class RegexViewModel: ViewModel() {
    private val regexListLiveData: MutableLiveData<List<RegexModel>> by lazy {
        MutableLiveData<List<RegexModel>>().also {
            loadRegex()
        }
    }

    private val regexModelList: MutableList<RegexModel> = mutableListOf(
        RegexModel("a", "Java", "a"),
        RegexModel("b", "Java", "b")
    )

    fun getRegexList(): LiveData<List<RegexModel>>   {
        return regexListLiveData
    }

    private fun loadRegex() {
        Handler().postDelayed({
            regexListLiveData.value = regexModelList
        }, 500)
    }

    fun addRegex(regexModel: RegexModel, position: Int) {
        Handler().postDelayed({
            regexModelList.add(position, regexModel)
            regexListLiveData.value = regexModelList
        }, 500)
    }

    fun deleteRegex(position: Int) {
        Handler().postDelayed({
            regexModelList.removeAt(position)
            regexListLiveData.value = regexModelList
        }, 500)
    }
}