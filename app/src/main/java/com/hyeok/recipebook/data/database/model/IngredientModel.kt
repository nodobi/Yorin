package com.hyeok.recipebook.data.database.model

import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import kotlinx.datetime.LocalDate

class IngredientModel(
    val id: Long,
    val name: String,
    val purchaseDay: Long,
    val expirationDay: Long?,
    val weight: Int,
    val weightUnit: String,
    val description: String,
)

fun IngredientModel.toUiModel(): IngredientUiModel =
    IngredientUiModel(
        id = id,
        name = name,
        purchaseDate = LocalDate.fromEpochDays(purchaseDay),
        expirationDate = expirationDay?.let { LocalDate.fromEpochDays(it) },
        weight = weight,
        weightUnit = weightUnit,
        description = description
    )

fun IngredientUiModel.toModel() =
    IngredientModel(
        id = id,
        name = name,
        purchaseDay = purchaseDate.toEpochDays(),
        expirationDay = expirationDate?.toEpochDays(),
        weight = weight,
        weightUnit = weightUnit,
        description = description
    )