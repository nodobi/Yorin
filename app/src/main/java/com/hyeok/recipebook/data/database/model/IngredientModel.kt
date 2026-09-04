package com.hyeok.recipebook.data.database.model

import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import kotlinx.datetime.LocalDate

class IngredientModel(
    val id: Long,
    val name: String,
    val purchaseDate: Long,
    val expirationDate: Long?,
    val weight: Int,
    val weightUnit: String,
    val description: String,
)

fun IngredientModel.toUiModel(): IngredientUiModel =
    IngredientUiModel(
        id = id,
        name = name,
        purchaseDate = LocalDate.fromEpochDays(purchaseDate),
        expirationDate = expirationDate?.let { LocalDate.fromEpochDays(it) },
        weight = weight,
        weightUnit = weightUnit,
        description = description
    )
