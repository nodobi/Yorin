package com.hyeok.recipebook.data.repository

import com.hyeok.recipebook.presentation.recipe.list.RecipeItemUiModel

interface RecipeRepository {

    suspend fun getRecipesByIngredient(ingredientId: Int): Result<List<RecipeItemUiModel>>
}