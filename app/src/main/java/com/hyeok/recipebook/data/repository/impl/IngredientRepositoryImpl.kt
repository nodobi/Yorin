package com.hyeok.recipebook.data.repository.impl

import com.hyeok.recipebook.data.database.model.IngredientModel
import com.hyeok.recipebook.data.repository.IngredientRepository
import com.hyeok.recipebook.data.source.IngredientLocalDataSource
import kotlinx.coroutines.flow.Flow

class IngredientRepositoryImpl(
    private val ingredientLocalDataSource: IngredientLocalDataSource,
) : IngredientRepository {
    override fun getIngredients(): Flow<List<IngredientModel>> {
        return ingredientLocalDataSource.getIngredients()
    }

    override fun getIngredient(ingredientId: Long): Flow<IngredientModel?> = ingredientLocalDataSource.getIngredient(ingredientId)


    override suspend fun removeIngredient(ingredientId: Long): Result<Unit> = runCatching {
        ingredientLocalDataSource.removeIngredient(ingredientId)
    }

    override suspend fun updateIngredient(ingredient: IngredientModel): Result<Unit> = runCatching {
        ingredientLocalDataSource.updateIngredient(ingredient)
    }

    override suspend fun addIngredient(
        name: String,
        purchaseDay: Long,
        expirationDay: Long?,
        weight: Int,
        weightUnit: String,
        description: String
    ): Result<Long> = runCatching {
        ingredientLocalDataSource.addIngredient(
            IngredientModel(
                id = 0,
                name = name,
                purchaseDay = purchaseDay,
                expirationDay = expirationDay,
                weight = weight,
                weightUnit = weightUnit,
                description = description
            )
        )
    }
}