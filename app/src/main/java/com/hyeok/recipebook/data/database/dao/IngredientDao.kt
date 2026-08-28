package com.hyeok.recipebook.data.database.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import com.hyeok.recipebook.data.database.entity.IngredientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {

    @Insert
    suspend fun insertIngredient(ingredient: IngredientEntity)

    @Query("DELETE from ingredient WHERE id = :id")
    suspend fun deleteIngredient(id: Long)

    @Query("SELECT * from ingredient WHERE id = :id")
    fun getIngredient(id: Long): Flow<IngredientEntity?>

    @Query("SELECT * FROM ingredient")
    fun getAllIngredients(): Flow<List<IngredientEntity>>
}