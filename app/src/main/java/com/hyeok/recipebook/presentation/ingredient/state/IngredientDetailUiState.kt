package com.hyeok.recipebook.presentation.ingredient.state

import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel

data class IngredientDetailUiState(
    val ingredient: IngredientUiModel,
    val remainExpirationDays: Int,
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
    }
}