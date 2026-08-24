package com.hyeok.recipebook.presentation.recipe.detail

data class RecipeDetailUiState(
    val isEditing: Boolean = false,
    val detail: RecipeUiModel
)