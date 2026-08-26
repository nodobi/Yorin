package com.hyeok.recipebook.data.repository

import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import kotlinx.coroutines.flow.Flow

interface IngredientRepository {

    fun getIngredients(): Flow<Result<List<IngredientUiModel>>>

    suspend fun getIngredient(ingredientId: Int): Result<IngredientUiModel>

    suspend fun removeIngredient(ingredientId: Int): Result<Unit>

    suspend fun addIngredient(ingredient: IngredientUiModel): Result<Unit>
}