package com.hyeok.recipebook.data.database.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "recipe")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val registerDate: Long,
    val cookingTime: Int,
    val photoUrl: String?,
)

