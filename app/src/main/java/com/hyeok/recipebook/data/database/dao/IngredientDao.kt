package com.hyeok.recipebook.data.database.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import com.hyeok.recipebook.data.database.entity.IngredientEntity
import com.hyeok.recipebook.data.database.model.IngredientModel
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {

    @Insert
    suspend fun insertIngredient(ingredient: IngredientEntity)

    @Query("DELETE from ingredient WHERE id = :id")
    suspend fun deleteIngredient(id: Long)

    @Query( """
        SELECT 
            ingredient.id AS id,
            ingredient.name AS name,
            ingredient.purchaseDate AS purchaseDate,
            ingredient.expirationDate AS expirationDate,
            ingredient.weight AS weight,
            weight_unit.name AS weightUnit,
            ingredient.description AS description
        FROM ingredient
        INNER JOIN weight_unit ON ingredient.weightUnitId = weight_unit.id
        WHERE ingredient.id = :ingredientId
    """)
    fun getIngredient(ingredientId: Long): Flow<IngredientModel>

    @Query("SELECT * FROM ingredient")
    fun getAllIngredients(): Flow<List<IngredientEntity>>
}