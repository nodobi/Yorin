package com.hyeok.recipebook.data.database.model

class IngredientModel(
    val id: Long,
    val name: String,
    val purchaseDay: Long,
    val expirationDay: Long?,
    val weight: Int,
    val weightUnit: String,
    val description: String,
)
