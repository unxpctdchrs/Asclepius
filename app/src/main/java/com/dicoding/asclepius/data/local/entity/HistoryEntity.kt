package com.dicoding.asclepius.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "histories")
class HistoryEntity (
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @field:ColumnInfo(name = "analysisImage")
    var analysisImage: String? = null,

    @field:ColumnInfo(name = "predictionResult")
    var predictionResult: String? = null,

    @field:ColumnInfo(name = "confidenceScore")
    var confidenceScore: String? = null,
)