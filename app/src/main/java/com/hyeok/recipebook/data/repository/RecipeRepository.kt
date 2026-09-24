package com.hyeok.recipebook.data.repository

import com.hyeok.recipebook.presentation.recipe.detail.RecipeUiModel
import com.hyeok.recipebook.presentation.recipe.detail.records.RecipeRecordUiModel
import com.hyeok.recipebook.presentation.recipe.list.RecipeItemUiModel
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getRecipesByIngredient(ingredientId: Long): Result<List<RecipeItemUiModel>>

    fun getRecipesSummaries(): Flow<Result<List<RecipeItemUiModel>>>

    fun getRecipeDetailsById(recipeId: Long): Flow<Result<RecipeUiModel>>

    suspend fun updateRecipe(recipe: RecipeUiModel): Result<Unit>

    suspend fun addRecipeRecord(recipeId: Long, record: RecipeRecordUiModel): Result<Unit>
}