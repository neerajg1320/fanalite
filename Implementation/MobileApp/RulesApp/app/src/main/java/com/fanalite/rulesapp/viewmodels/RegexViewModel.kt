package com.fanalite.rulesapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fanalite.rulesapp.models.Language
import com.fanalite.rulesapp.retrofitRegexValidate.RegexValidateService
import com.fanalite.rulesapp.retrofitRegexValidate.models.RegexValidateRequest
import com.fanalite.rulesapp.roomAppDatabase.AppDatabase
import com.fanalite.rulesapp.models.RegexModel
import com.fanalite.rulesapp.repository.FanaliteAuthRepository
import com.fanalite.rulesapp.repository.RemoteFirebaseRepository
import com.fanalite.rulesapp.repository.RegexLocalRoomRepository
import com.fanalite.rulesapp.repository.FanaliteResourceRepository
import com.fanalite.rulesapp.view.TAG
import kotlinx.coroutines.*
import java.net.ConnectException

class RegexViewModel(application: Application): AndroidViewModel(application) {

    private val regexDao = AppDatabase.getDatabase(application).regexDao()
    private val localRepository = RegexLocalRoomRepository(regexDao)
    private val remoteFirebaseRepository = RemoteFirebaseRepository()
    private var fanaliteRepository:FanaliteResourceRepository? = null

    // We retrieve the Tokens here from SharedPreferences
    // This way we keep the repository code independent of the Android Context
    init {
        val sessionManager = SessionManager(application)
        val authToken = sessionManager.fetchAuthToken()
        if (authToken != null) {
            fanaliteRepository = FanaliteResourceRepository("Bearer", authToken);
        }
    }

    private val localEnabled = false
    private val remoteEnabled = true
    // firebase, fanalite-server
    private val remoteEnd = "fanalite-server";

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


                if (remoteEnd == "firebase") {
                    regexList =
                        remoteFirebaseRepository.getAllData(RegexModel::class.java) as List<RegexModel>
                } else if (remoteEnd == "fanalite-server") {
                    try {
                        if (fanaliteRepository != null) {
                            regexList = fanaliteRepository!!.getAllRules();
                        }
                    } catch (e: ConnectException) {
                        Log.e(TAG, e.localizedMessage);
                    }
                }

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
            id = remoteFirebaseRepository.generateId()
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
                // remoteFirebaseRepository.insertData(regexModel.id, regexModel)
                if (fanaliteRepository != null) {
                    fanaliteRepository!!.insertRule(regexModel)
                }
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
                // remoteFirebaseRepository.updateData(regexModel.id, regexModel)
                if (fanaliteRepository != null) {
                    fanaliteRepository!!.updateRule(regexModel.id, regexModel)
                }
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
                // remoteFirebaseRepository.deleteData(regexModel.id)
                if (fanaliteRepository != null) {
                    fanaliteRepository!!.deleteRule(regexModel.id);
                }
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
                remoteFirebaseRepository.deleteAll()
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
                Language.JAVA -> RegexValidateService.getRegexJavaApi()
                Language.PYTHON -> RegexValidateService.getRegexPythonApi()
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