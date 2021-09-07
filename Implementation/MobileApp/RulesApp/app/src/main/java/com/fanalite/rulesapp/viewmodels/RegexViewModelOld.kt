package com.fanalite.rulesapp.viewmodels

import android.os.Handler
import android.util.Log
import android.util.Log.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fanalite.rulesapp.view.TAG
import com.fanalite.rulesapp.models.RegexModel


class RegexViewModelOld: ViewModel() {
    private val regexListLiveData: MutableLiveData<List<RegexModel>> by lazy {
        MutableLiveData<List<RegexModel>>().also {
            loadRegexList()
        }
    }

    private var regexModelList: MutableList<RegexModel> = mutableListOf()

    fun getRegexList(): LiveData<List<RegexModel>>   {
        return regexListLiveData
    }

    fun loadRegexList() {
        Handler().postDelayed({
            Log.d(TAG, "regexModelList:size = ${regexModelList.size}")
            regexListLiveData.value = ArrayList<RegexModel>(regexModelList)
        }, 500)
    }

    fun addRegex(regexModel: RegexModel, position: Int) {

        Handler().postDelayed({
            regexModelList.add(position, regexModel)

            regexModelList.forEach {
                d(TAG, it.toString())
            }

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