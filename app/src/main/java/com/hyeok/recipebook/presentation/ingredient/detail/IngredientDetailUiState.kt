package com.hyeok.recipebook.presentation.ingredient.detail

import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel

data class IngredientDetailUiState(
    val ingredient: IngredientUiModel,
    val remainExpirationDays: Int?,
    val expirationProgress: Float,
    val relatedRecipes: List<String>
) {
    companion object {
        fun fake(): IngredientDetailUiState =
            IngredientDetailUiState(
                ingredient = IngredientUiModel.fake(),
                remainExpirationDays = 5,
                expirationProgress = 0.5f,
                relatedRecipes = emptyList()
            )

        fun empty(): IngredientDetailUiState =
            IngredientDetailUiState(
                ingredient = IngredientUiModel.empty(),
                remainExpirationDays = 0,
                expirationProgress = 0f,
                relatedRecipes = emptyList()
            )
    }
}