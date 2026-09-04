package com.hyeok.recipebook.data.repository.impl

import android.util.Log
import com.hyeok.recipebook.data.repository.RecipeRepository
import com.hyeok.recipebook.data.source.RecipeLocalDataSource
import com.hyeok.recipebook.presentation.recipe.list.RecipeItemUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeLocalDataSource: RecipeLocalDataSource
): RecipeRepository {
    override suspend fun getRecipesByIngredient(ingredientId: Long): Result<List<RecipeItemUiModel>> {
        TODO("Not yet implemented")
    }

    override fun getRecipes(): Flow<Result<List<RecipeItemUiModel>>> {
        return recipeLocalDataSource.getRecipesWithIngredients()
            .map { recipes ->
                val recipeItem = recipes.map {
                    RecipeItemUiModel(
                        id = it.recipe.id,
                        name = it.recipe.name,
                        photoUri = it.recipe.photoUrl,
                        ingredients = it.ingredients.map { it.name },
                        averageScore = it.averageScore,
                        cookingTime = it.recipe.cookingTime
                    )
                }
                Result.success(recipeItem)
            }
            .catch {
                Result.failure<List<RecipeItemUiModel>>(it)
            }
    }
}