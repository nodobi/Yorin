package com.hyeok.recipebook.data.repository.impl

import com.hyeok.recipebook.data.repository.RecipeRepository
import com.hyeok.recipebook.presentation.recipe.list.RecipeItemUiModel

class RecipeRepositoryImpl: RecipeRepository {
    override suspend fun getRecipesByIngredient(ingredientId: Long): Result<List<RecipeItemUiModel>> {
        TODO("Not yet implemented")
    }
}