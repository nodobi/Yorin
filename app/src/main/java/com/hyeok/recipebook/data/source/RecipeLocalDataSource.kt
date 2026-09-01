package com.hyeok.recipebook.data.source

import com.hyeok.recipebook.data.database.dao.RecipeDao
import com.hyeok.recipebook.data.database.model.RecipesWithIngredients
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeLocalDataSource @Inject constructor(
    private val recipeDao: RecipeDao
) {
    fun getRecipesWithIngredients(): Flow<List<RecipesWithIngredients>> {
        return recipeDao.getRecipes()
    }
}