package com.hyeok.recipebook.data.repository

import com.hyeok.recipebook.presentation.recipe.list.RecipeItemUiModel
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getRecipesByIngredient(ingredientId: Long): Result<List<RecipeItemUiModel>>

    fun getRecipes(): Flow<Result<List<RecipeItemUiModel>>>
}