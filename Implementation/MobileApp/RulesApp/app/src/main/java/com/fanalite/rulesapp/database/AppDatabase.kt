package com.fanalite.rulesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fanalite.rulesapp.models.RegexModel


@Database(entities = [RegexModel::class], version = 1, exportSchema = false)
@TypeConverters(LanguageConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun regexDao(): RegexDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: kotlin.synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "regex_database"
            ).build()
    }
}