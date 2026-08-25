package com.hyeok.recipebook.data.repository

import com.hyeok.recipebook.presentation.recipe.list.RecipeItemUiModel

interface RecipeRepository {

    fun getRecipesByIngredient(ingredientId: Int): List<RecipeItemUiModel>
}