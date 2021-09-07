package com.fanalite.rulesapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.fanalite.rulesapp.data.models.RegexModel
import com.fanalite.rulesapp.data.repository.RegexRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegexViewModel(application: Application): AndroidViewModel(application) {
    private val regexDao = RegexDatabase.getDatabase(application).regexDao()
    private val repository: RegexRepository = RegexRepository(regexDao)

    val getAllData: LiveData<List<RegexModel>> = repository.getAllData

    fun insertData(regexModel: RegexModel) {
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
}