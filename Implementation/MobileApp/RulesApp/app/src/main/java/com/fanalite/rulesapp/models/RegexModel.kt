package com.fanalite.rulesapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "regex_table")
@Parcelize
data class RegexModel (
    @PrimaryKey()
    var id:String,
    val title:String,
    val language:Language,
    val regex:String
): Parcelable {
    // For Firebase retrieve Map to RegexModel
    constructor() : this("","",Language.UNKNOWN, "")
}