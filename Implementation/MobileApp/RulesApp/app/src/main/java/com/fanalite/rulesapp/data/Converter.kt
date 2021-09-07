package com.fanalite.rulesapp.data

import androidx.room.TypeConverter
import com.fanalite.rulesapp.data.models.Language


class Converter {
    @TypeConverter
    fun fromLanguage(language: Language): String {
        return language.name
    }

    @TypeConverter
    fun toLanguage(language: String): Language {
        return Language.valueOf(language)
    }
}