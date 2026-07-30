package com.hyeok.recipebook.presentation.recipe.list

data class RecipesUiState(
    val selectedSearchFilter: RecipeSearchFilter = RecipeSearchFilter.BY_RECIPE,
    val recipes: List<RecipeItemUiModel>,
)