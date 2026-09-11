package com.hyeok.recipebook.data.repository.impl

import com.hyeok.recipebook.data.database.model.toModel
import com.hyeok.recipebook.data.database.model.toUiModel
import com.hyeok.recipebook.data.repository.IngredientRepository
import com.hyeok.recipebook.data.source.IngredientLocalDataSource
import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class IngredientRepositoryImpl(
    private val ingredientLocalDataSource: IngredientLocalDataSource,
) : IngredientRepository {
    override fun getIngredients(): Flow<Result<List<IngredientUiModel>>> {
        return ingredientLocalDataSource
            .getIngredients()
            .map { Result.success(it.map { it.toUiModel() }) }
            .catch { Result.failure<List<IngredientUiModel>>(it) }
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