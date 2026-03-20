package com.hyeok.recipebook.presentation.recipe.list

data class RecipeUiState(
    val id: Int,
    val name: String,
    val photoUri: String? = null,
    val ingredients: List<String>,
    val averageScore: Float,
    val cookingTime: Int,
)