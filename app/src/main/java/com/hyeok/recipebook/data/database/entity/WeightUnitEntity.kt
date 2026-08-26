package com.hyeok.recipebook.data.database.entity

import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "weight_unit",
    indices = [Index(value = ["name"], unique = true)]
)
data class WeightUnitEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)