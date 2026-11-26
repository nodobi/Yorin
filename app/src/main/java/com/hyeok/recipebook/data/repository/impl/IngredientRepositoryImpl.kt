package com.hyeok.recipebook.data.repository.impl

import com.hyeok.recipebook.data.database.model.IngredientModel
import com.hyeok.recipebook.data.repository.IngredientRepository
import com.hyeok.recipebook.data.source.IngredientLocalDataSource
import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import com.hyeok.recipebook.presentation.ingredient.model.toModel
import com.hyeok.recipebook.presentation.ingredient.model.toUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class IngredientRepositoryImpl(
    private val ingredientLocalDataSource: IngredientLocalDataSource,
) : IngredientRepository {
    override fun getIngredients(): Flow<List<IngredientModel>> {
        return ingredientLocalDataSource.getIngredients()
    }

    override fun getIngredient(ingredientId: Long): Flow<Result<IngredientUiModel?>> {
        return ingredientLocalDataSource
            .getIngredient(ingredientId)
            .map { Result.success(it.toUiModel()) }
            .catch { Result.failure<IngredientUiModel>(it) }
    }

    override suspend fun removeIngredient(ingredientId: Long): Result<Unit> = runCatching {
        ingredientLocalDataSource.removeIngredient(ingredientId)
    }

    override suspend fun addIngredient(ingredient: IngredientUiModel): Result<Unit> = runCatching {
        ingredientLocalDataSource.addIngredient(ingredient.toModel())
    }
}