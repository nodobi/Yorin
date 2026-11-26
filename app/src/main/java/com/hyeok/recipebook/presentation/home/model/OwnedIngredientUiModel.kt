package com.hyeok.recipebook.presentation.home.model

import androidx.compose.runtime.Stable
import com.hyeok.recipebook.data.database.model.IngredientModel
import kotlinx.datetime.LocalDate

/**
 * 사용자가 보유중인 재료를 나타내는 model, IngredientUiModel 에서 daysToExpiration 가 추가되었다.
 * IngredientUiModel 대체하는 것 고려
 */
@Stable
data class OwnedIngredientUiModel(
    val id: Long,
    val name: String,
    val purchaseDate: LocalDate,
    val expirationDate: LocalDate?,
    val daysToExpiration: Int?,
    val weight: Int,
    val weightUnit: String,
    val description: String
)

fun IngredientModel.toOwnIngredientUiModel(
    daysToExpiration: Int?
): OwnedIngredientUiModel = OwnedIngredientUiModel(
    id = id,
    name = name,
    purchaseDate = LocalDate.fromEpochDays(purchaseDay),
    expirationDate = expirationDay?.let { LocalDate.fromEpochDays(it) },
    daysToExpiration = daysToExpiration,
    weight = weight,
    weightUnit = weightUnit,
    description = description
)