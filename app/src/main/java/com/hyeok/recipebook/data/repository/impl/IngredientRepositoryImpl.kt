package com.hyeok.recipebook.data.repository.impl

import com.hyeok.recipebook.data.repository.IngredientRepository
import com.hyeok.recipebook.data.source.IngredientLocalDataSource
import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import kotlinx.coroutines.flow.Flow

class IngredientRepositoryImpl(
    private val ingredientLocalDataSource: IngredientLocalDataSource
): IngredientRepository {
    override fun getIngredients(): Flow<Result<List<IngredientUiModel>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getIngredient(ingredientId: Int): Result<IngredientUiModel> {
        TODO("Not yet implemented")
    }

    override suspend fun removeIngredient(ingredientId: Int): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun addIngredient(ingredient: IngredientUiModel): Result<Unit> {
        TODO("Not yet implemented")
    }
}