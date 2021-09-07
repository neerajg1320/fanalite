package com.fanalite.rulesapp.database

import androidx.room.TypeConverter
import com.fanalite.rulesapp.models.Language


class LanguageConverter {
    @TypeConverter
    fun fromLanguage(language: Language): String {
        return language.name
    }

    @TypeConverter
    fun toLanguage(language: String): Language {
        return Language.valueOf(language)
    }
}