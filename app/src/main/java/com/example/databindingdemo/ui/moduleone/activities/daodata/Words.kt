package com.example.databindingdemo.ui.moduleone.activities.daodata

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_word")
data class Words(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    var word: String
)