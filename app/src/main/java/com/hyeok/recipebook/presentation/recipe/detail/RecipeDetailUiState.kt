package com.hyeok.recipebook.presentation.recipe.detail

data class RecipeDetailUiState(
    val isEditing: Boolean = false,
    val recipe: RecipeUiModel
) {
    companion object {
        fun fake(): RecipeDetailUiState = RecipeDetailUiState(
            isEditing = false,
            recipe = RecipeUiModel.fake()
        )
    }
}