package com.fanalite.rulesapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fanalite.rulesapp.models.Language
import com.fanalite.rulesapp.retrofitRegex.RegexService
import com.fanalite.rulesapp.retrofitRegex.models.RegexValidateRequest
import com.fanalite.rulesapp.roomAppDatabase.AppDatabase
import com.fanalite.rulesapp.models.RegexModel
import com.fanalite.rulesapp.repository.RemoteFirebaseRepository
import com.fanalite.rulesapp.repository.RegexLocalRoomRepository
import com.fanalite.rulesapp.view.TAG
import kotlinx.coroutines.*

class RegexViewModel(application: Application): AndroidViewModel(application) {
    private val regexDao = AppDatabase.getDatabase(application).regexDao()
    private val localRepository = RegexLocalRoomRepository(regexDao)
    private val remoteRepository = RemoteFirebaseRepository()

    private val localEnabled = true
    private val remoteEnabled = false

    private var job: Job? = null
    private val mutableLiveData: MutableLiveData<List<RegexModel>> = MutableLiveData()

    var allData:LiveData<List<RegexModel>>? = null

    init {
        if (localEnabled) {
            allData = localRepository.getAllData
        }
        if (remoteEnabled) {
            allData = mutableLiveData
        }
    }

    fun queryData() {
        if (remoteEnabled) {
            viewModelScope.launch(Dispatchers.IO) {
                var regexList: List<RegexModel> = emptyList()


                regexList = remoteRepository.getAllData(RegexModel::class.java) as List<RegexModel>


                Log.d(TAG, "RegexViewModel: regexList: $regexList")
                withContext(Dispatchers.Main) {
                    mutableLiveData.setValue(regexList)
                }
            }
        }
    }

    fun generateId(): String? {
        var id: String? = null

        if (localEnabled) {
            id = localRepository.generateId()
        }
        if (remoteEnabled) {
            id = remoteRepository.generateId()
        }

        return id
    }

    fun insertData(regexModel: RegexModel) {
        validateRegex(regexModel.regex, regexModel.language)

        viewModelScope.launch(Dispatchers.IO) {
            if (localEnabled) {
                localRepository.insertData(regexModel)
            }
            if (remoteEnabled) {
                remoteRepository.insertData(regexModel.id, regexModel)
            }
            withContext(Dispatchers.Main) {
                // Inform the view
            }
            queryData()
        }
    }

    fun updateData(regexModel: RegexModel) {
        viewModelScope.launch(Dispatchers.IO) {
            if (localEnabled) {
                localRepository.updateData(regexModel)
            }
            if (remoteEnabled) {
                remoteRepository.updateData(regexModel.id, regexModel)
            }
            withContext(Dispatchers.Main) {
                // Inform the view
            }
            queryData()
        }
    }

    fun deleteItem(regexModel: RegexModel) {
        viewModelScope.launch(Dispatchers.IO) {
            if (localEnabled) {
                localRepository.deleteItem(regexModel)
            }
            if (remoteEnabled) {
                remoteRepository.deleteData(regexModel.id)
            }
            withContext(Dispatchers.Main) {
                // Inform the view
            }
            queryData()
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            if (localEnabled) {
                localRepository.deleteAll()
            }
            if (remoteEnabled) {
                remoteRepository.deleteAll()
            }
            withContext(Dispatchers.Main) {
                // Inform the view
            }
            queryData()
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

    fun validateRegex(regexStr: String, language: Language) {
        val regexService = when (language) {
                Language.JAVA -> RegexService.getRegexJavaApi()
                Language.PYTHON -> RegexService.getRegexPythonApi()
                else -> null
            }

        regexService?.let { service ->
            job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val regexRequest = RegexValidateRequest(regexStr)
                val response = service.validateRegex(regexRequest)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val regexValidateResponse = response.body()
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
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}