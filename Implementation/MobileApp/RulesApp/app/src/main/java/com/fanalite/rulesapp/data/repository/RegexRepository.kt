package com.fanalite.rulesapp.data.repository

import androidx.lifecycle.LiveData
import com.fanalite.rulesapp.data.RegexDao
import com.fanalite.rulesapp.data.models.RegexModel

class RegexRepository (private val todoDao: RegexDao){
    val getAllData: LiveData<List<RegexModel>> = todoDao.getAllData()

    suspend fun insertData(regexModel: RegexModel) {
        todoDao.insertData(regexModel)
    }

    suspend fun updateData(regexModel: RegexModel) {
        todoDao.updateData(regexModel)
    }

    suspend fun deleteItem(regexModel: RegexModel) {
        todoDao.deleteItem(regexModel)
    }

    suspend fun deleteAll() {
        todoDao.deleteAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<RegexModel>> {
        return todoDao.searchDatabase(searchQuery)
    }

//    fun sortByHighPriority(): LiveData<List<RegexModel>> {
//        return todoDao.sortByHighPriority()
//    }
//
//    fun sortByLowPriority(): LiveData<List<RegexModel>> {
//        return todoDao.sortByLowPriority()
//    }
}