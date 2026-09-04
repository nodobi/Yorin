package com.hyeok.recipebook.data.source

import com.hyeok.recipebook.data.database.dao.IngredientDao
import com.hyeok.recipebook.data.database.dao.WeightUnitDao
import com.hyeok.recipebook.data.database.entity.IngredientEntity
import com.hyeok.recipebook.data.database.model.IngredientModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IngredientLocalDataSource @Inject constructor(
    private val ingredientDao: IngredientDao,
    private val weightUnitDao: WeightUnitDao
) {
    fun getIngredients(): Flow<List<IngredientModel>> {
        return ingredientDao.getAllIngredients()
    }

    fun getIngredient(ingredientId: Long): Flow<IngredientModel> {
        return ingredientDao.getIngredient(ingredientId)
    }

    suspend fun removeIngredient(ingredientId: Long) {
        ingredientDao.deleteIngredient(ingredientId)
    }

    suspend fun addIngredient(ingredientModel: IngredientModel) {
        ingredientDao.insertIngredient(
            IngredientEntity(
                id = ingredientModel.id,
                name = ingredientModel.name,
                purchaseDate = ingredientModel.purchaseDate,
                expirationDate = ingredientModel.expirationDate,
                weight = ingredientModel.weight,
                weightUnitId = weightUnitDao.getIdByName(ingredientModel.weightUnit),
                description = ingredientModel.description,
            )
        )
    }
}