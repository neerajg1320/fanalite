package com.fanalite.rulesapp.roomAppDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fanalite.rulesapp.models.RegexModel

@Dao
interface RegexDao {
    @Query("SELECT * FROM regex_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<RegexModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: RegexModel)

    @Update
    suspend fun updateData(toDoData: RegexModel)

    @Delete
    suspend fun deleteItem(toDoData: RegexModel)

    @Query("DELETE FROM regex_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM regex_table WHERE title LIKE :searchQuery")
    fun searchByTitle(searchQuery: String): LiveData<List<RegexModel>>


    @Query("SELECT * FROM regex_table ORDER BY language ASC ")
    fun sortByLanguageAsc(): LiveData<List<RegexModel>>

    @Query("SELECT * FROM regex_table ORDER BY language DESC ")
    fun sortByLanguageDesc(): LiveData<List<RegexModel>>

}