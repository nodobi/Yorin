package com.hyeok.recipebook.presentation.recipe.detail

import com.hyeok.recipebook.presentation.recipe.model.RecipeUiModel

sealed interface RecipeDetailUiState {
    data object Loading: RecipeDetailUiState

    data class Success(
        val recipe: RecipeUiModel,
    ): RecipeDetailUiState
}