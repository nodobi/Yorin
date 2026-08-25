package com.hyeok.recipebook.data.repository.impl

import com.hyeok.recipebook.data.repository.IngredientRepository
import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import kotlinx.coroutines.flow.Flow

class IngredientRepositoryImpl: IngredientRepository {
    override fun getIngredients(): Flow<List<IngredientUiModel>> {
        TODO("Not yet implemented")
    }

    override fun getIngredient(ingredientId: Int): IngredientUiModel {
        TODO("Not yet implemented")
    }

    override fun removeIngredient(ingredientId: Int) {
        TODO("Not yet implemented")
    }

    override fun addIngredient(ingredient: IngredientUiModel) {
        TODO("Not yet implemented")
    }
}