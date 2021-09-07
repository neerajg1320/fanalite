package com.fanalite.rulesapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fanalite.rulesapp.data.models.RegexModel


@Database(entities = [RegexModel::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class RegexDatabase : RoomDatabase() {

    abstract fun regexDao(): RegexDao

    companion object {
        @Volatile
        private var INSTANCE: RegexDatabase? = null

        fun getDatabase(context: Context): RegexDatabase =
            INSTANCE ?: kotlin.synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RegexDatabase::class.java,
                "regex_database"
            ).build()
    }
}