package com.fanalite.rulesapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "regex_table")
@Parcelize
data class RegexModel (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    val name:String,
    val type:Language,
    val regex:String
): Parcelable