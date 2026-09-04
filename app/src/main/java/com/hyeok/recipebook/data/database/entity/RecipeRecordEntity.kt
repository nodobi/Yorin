package com.hyeok.recipebook.data.database.entity

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "recipe_record",
    foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [Index("recipeId")]
)
data class RecipeRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cookedAt: Long,
    val title: String,
    val description: String,
    val score: Int,
    val recipeId: Long,
)