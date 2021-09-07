package com.fanalite.rulesapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.fanalite.rulesapp.database.AppDatabase
import com.fanalite.rulesapp.models.RegexModel
import com.fanalite.rulesapp.repository.RegexRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegexViewModel(application: Application): AndroidViewModel(application) {
    private val regexDao = AppDatabase.getDatabase(application).regexDao()
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