package com.hyeok.recipebook.presentation.ingredient.edit

import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import kotlin.time.ExperimentalTime

data class IngredientEditUiState(
    val ingredient: IngredientUiModel?,
) {
    companion object {
        @OptIn(ExperimentalTime::class)
        fun fake(): IngredientEditUiState = IngredientEditUiState(
            ingredient = IngredientUiModel.fake(),
        )

        fun empty(): IngredientEditUiState = IngredientEditUiState(
            ingredient = null,
        )
    }
}
