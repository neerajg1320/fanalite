package com.fanalite.rulesapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.fanalite.rulesapp.retrofitRegex.RegexService
import com.fanalite.rulesapp.retrofitRegex.models.RegexValidateRequest
import com.fanalite.rulesapp.roomAppDatabase.AppDatabase
import com.fanalite.rulesapp.models.RegexModel
import com.fanalite.rulesapp.repository.RegexRepository
import com.fanalite.rulesapp.view.TAG
import kotlinx.coroutines.*

class RegexViewModel(application: Application): AndroidViewModel(application) {
    private val regexDao = AppDatabase.getDatabase(application).regexDao()
    private val repository: RegexRepository = RegexRepository(regexDao)

    var job: Job? = null
    private val regexService = RegexService.getRegexService()

    val getAllData: LiveData<List<RegexModel>> = repository.getAllData

    fun insertData(regexModel: RegexModel) {
        validateRegex(regexModel.regex)
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(regexModel)
        }
    }

    fun updateData(regexModel: RegexModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(regexModel)
        }
    }

    fun deleteItem(regexModel: RegexModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(regexModel)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun searchByTitle(searchQuery: String): LiveData<List<RegexModel>> {
        return regexDao.searchByTitle(searchQuery)
    }

    fun sortByLanguageAsc(): LiveData<List<RegexModel>> {
        return regexDao.sortByLanguageAsc()
    }

    fun sortByLanguageDesc(): LiveData<List<RegexModel>> {
        return regexDao.sortByLanguageDesc()
    }

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.d(TAG, "Exception: ${throwable.localizedMessage}")
    }

    fun validateRegex(regexStr: String) {

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val regexRequest = RegexValidateRequest(regexStr)
            val response = regexService.validateRegex(regexRequest)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val regexValidateResponse =  response.body()
                    if (regexValidateResponse?.status == "SUCCESS") {
                        val regexValidateResult = regexValidateResponse?.result
                        Log.d(TAG, "Regex Service Response: ${regexValidateResult}")
                    }
                } else {
                    Log.d(TAG, "Error: ${response.message()}")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}