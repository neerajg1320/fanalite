package com.fanalite.rulesapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fanalite.rulesapp.data.models.RegexModel

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

    @Query("SELECT * FROM regex_table WHERE name LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<RegexModel>>

}