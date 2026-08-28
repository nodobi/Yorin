package com.hyeok.recipebook.data.repository.impl

import com.hyeok.recipebook.data.database.entity.IngredientEntity
import com.hyeok.recipebook.data.repository.IngredientRepository
import com.hyeok.recipebook.data.source.IngredientLocalDataSource
import com.hyeok.recipebook.data.source.WeightUnitLocalDataSource
import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

class IngredientRepositoryImpl(
    private val ingredientLocalDataSource: IngredientLocalDataSource,
    private val weightUnitLocalDataSource: WeightUnitLocalDataSource
) : IngredientRepository {
    override fun getIngredients(): Flow<Result<List<IngredientUiModel>>> {

        return ingredientLocalDataSource
            .getIngredients()
            .map { ingredientEntities ->
                val weightUnits = weightUnitLocalDataSource.getAllUnits().associate { it.id to it.name }

                Result.success(ingredientEntities.map {
                    IngredientUiModel(
                        id = it.id,
                        name = it.name,
                        purchaseDate = LocalDate.fromEpochDays(it.purchaseDate),
                        expirationDate = it.expirationDate?.let { LocalDate.fromEpochDays(it)},
                        weight = it.weight,
                        weightUnit = weightUnits[it.weightUnitId]!!,
                        description = it.description
                    )
                })
            }
            .catch { Result.failure<List<IngredientUiModel>>(it) }

    }

    override fun getIngredient(ingredientId: Long): Flow<Result<IngredientUiModel?>> {
        return ingredientLocalDataSource
            .getIngredient(ingredientId)
            .map { ingredientEntity ->
                val weightUnitName = ingredientEntity?.weightUnitId?.let {
                    weightUnitLocalDataSource.getUnitById(it)
                } ?: ""

                Result.success(
                    ingredientEntity?.let {
                        IngredientUiModel(
                            id = it.id,
                            name = it.name,
                            purchaseDate = LocalDate.fromEpochDays(it.purchaseDate),
                            expirationDate = it.expirationDate?.let { LocalDate.fromEpochDays(it) },
                            weight = it.weight,
                            weightUnit = weightUnitName,
                            description = it.description
                        )
                    }
                )
            }
            .catch { Result.failure<IngredientUiModel?>(it)}
    }

    override suspend fun removeIngredient(ingredientId: Long): Result<Unit> = runCatching {
        ingredientLocalDataSource.removeIngredient(ingredientId)
    }

    override suspend fun addIngredient(ingredient: IngredientUiModel): Result<Unit> = runCatching {
        val unitId = weightUnitLocalDataSource.getUnitByName(ingredient.weightUnit)
        ingredientLocalDataSource.addIngredient(
            IngredientEntity(
                name = ingredient.name,
                purchaseDate = ingredient.purchaseDate.toEpochDays(),
                expirationDate = ingredient.expirationDate?.toEpochDays(),
                weight = ingredient.weight,
                weightUnitId = unitId,
                description = ingredient.description
            )
        )
    }
}