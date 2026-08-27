package com.hyeok.recipebook.data.source

import com.hyeok.recipebook.data.database.AppDatabase
import com.hyeok.recipebook.data.database.dao.IngredientDao
import com.hyeok.recipebook.data.database.entity.IngredientEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IngredientLocalDataSource @Inject constructor(
    private val ingredientDao: IngredientDao
) {
    fun getIngredients(): Flow<List<IngredientEntity>> {
        return ingredientDao.getAllIngredients()
    }

    fun getIngredient(ingredientId: Long): Flow<IngredientEntity> {
        return ingredientDao.getIngredient(ingredientId)
    }

    suspend fun removeIngredient(ingredientId: Long) {
        ingredientDao.deleteIngredient(ingredientId)
    }

    suspend fun addIngredient(ingredient: IngredientEntity) {
        ingredientDao.insertIngredient(ingredient)
    }
}