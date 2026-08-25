package com.hyeok.recipebook.data.repository

import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import kotlinx.coroutines.flow.Flow

interface IngredientRepository {

    fun getIngredients(): Flow<List<IngredientUiModel>>

    fun getIngredient(ingredientId: Int): IngredientUiModel

    fun removeIngredient(ingredientId: Int)

    fun addIngredient(ingredient: IngredientUiModel)
}