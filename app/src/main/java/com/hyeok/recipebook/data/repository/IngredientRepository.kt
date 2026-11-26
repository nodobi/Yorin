package com.hyeok.recipebook.data.repository

import com.hyeok.recipebook.data.database.model.IngredientModel
import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import kotlinx.coroutines.flow.Flow

interface IngredientRepository {

    fun getIngredients(): Flow<List<IngredientModel>>

    fun getIngredient(ingredientId: Long): Flow<IngredientModel?>

    suspend fun removeIngredient(ingredientId: Long): Result<Unit>

    suspend fun addIngredient(ingredient: IngredientUiModel): Result<Unit>
}