package com.hyeok.recipebook.data.database.entity

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "ingredient",
    foreignKeys = [
        ForeignKey(
            entity = WeightUnitEntity::class,
            parentColumns = ["id"],
            childColumns = ["weightUnitId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index("weightUnitId")]
)
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val purchaseDate: Long,
    val expirationDate: Long?,
    val weight: Int,
    val weightUnitId: Long,
    val description: String,
)
