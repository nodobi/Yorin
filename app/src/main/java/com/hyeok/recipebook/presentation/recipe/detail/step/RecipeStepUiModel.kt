package com.hyeok.recipebook.presentation.recipe.detail.step

// TODO:: 순서 변경은 나중에 구현
data class RecipeStepUiModel(
    val id: Int,
    val order: Int,
    val description: String
)