package com.hyeok.recipebook.presentation.ingredient.state

import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import com.hyeok.recipebook.presentation.util.ext.toEpochMilliseconds
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class IngredientEditUiState(
    val ingredient: IngredientUiModel?,
    val currentUtcMills: Long,
    val purchaseUtcMills: Long,
    val expirationUtcMills: Long,
) {
    companion object {
        @OptIn(ExperimentalTime::class)
        fun fake(): IngredientEditUiState = IngredientEditUiState(
            ingredient = IngredientUiModel.fake(),
            currentUtcMills = Clock.System.now().toEpochMilliseconds(),
            purchaseUtcMills = IngredientUiModel.fake().purchaseDate.toEpochMilliseconds(),
            expirationUtcMills = IngredientUiModel.fake().expirationDate.toEpochMilliseconds()
        )
    }
}
