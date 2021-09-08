package com.fanalite.rulesapp.repository

import androidx.lifecycle.LiveData
import com.fanalite.rulesapp.roomAppDatabase.RegexDao
import com.fanalite.rulesapp.models.RegexModel
import java.util.*

class RegexRepository (private val todoDao: RegexDao){
    val getAllData: LiveData<List<RegexModel>> = todoDao.getAllData()

    fun generateId():String {
        return UUID.randomUUID().toString()
    }

    // This function has been created for compatibility with Firebase API
    suspend fun insertData(id:String, data: RegexModel) {
        data.id = id
        return insertData(data)
    }

    // If we call this function then it must have an id
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
        return todoDao.searchByTitle(searchQuery)
    }

    fun sortByLanguageAsc(): LiveData<List<RegexModel>> {
        return todoDao.sortByLanguageAsc()
    }

    fun sortByLanguageDesc(): LiveData<List<RegexModel>> {
        return todoDao.sortByLanguageDesc()
    }
}