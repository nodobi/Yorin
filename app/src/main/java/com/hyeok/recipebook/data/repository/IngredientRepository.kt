package com.hyeok.recipebook.data.repository

import com.hyeok.recipebook.data.database.model.IngredientModel
import kotlinx.coroutines.flow.Flow

interface IngredientRepository {

    fun getIngredients(): Flow<List<IngredientModel>>

    fun getIngredient(ingredientId: Long): Flow<IngredientModel?>

    suspend fun removeIngredient(ingredientId: Long): Result<Unit>

    suspend fun updateIngredient(ingredient: IngredientModel): Result<Unit>

    suspend fun addIngredient(
        name: String,
        purchaseDay: Long,
        expirationDay: Long?,
        weight: Int,
        weightUnit: String,
        description: String,
    ): Result<Long>
}