package com.hyeok.recipebook.data.repository

import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import kotlinx.coroutines.flow.Flow

interface IngredientRepository {

    fun getIngredients(): Flow<Result<List<IngredientUiModel>>>

    fun getIngredient(ingredientId: Long): Flow<Result<IngredientUiModel>>

    suspend fun removeIngredient(ingredientId: Long): Result<Unit>

    suspend fun addIngredient(ingredient: IngredientUiModel): Result<Unit>
}