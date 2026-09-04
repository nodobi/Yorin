package com.hyeok.recipebook.data.database.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Transaction
import com.hyeok.recipebook.data.database.entity.RecipeEntity
import com.hyeok.recipebook.data.database.entity.RecipeIngredientEntity
import com.hyeok.recipebook.data.database.entity.RecipeStepEntity
import com.hyeok.recipebook.data.database.model.RecipesWithIngredients
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert
    fun addRecipe(recipe: RecipeEntity)

    @Insert
    fun addRecipeIngredient(ingredients: List<RecipeIngredientEntity>)

    @Insert
    fun addRecipeSteps(steps: List<RecipeStepEntity>)

    @Transaction
    @Query("SELECT * FROM recipe")
    fun getRecipes(): Flow<List<RecipesWithIngredients>>
}