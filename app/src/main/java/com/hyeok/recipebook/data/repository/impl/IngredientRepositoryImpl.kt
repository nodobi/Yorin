package com.hyeok.recipebook.data.repository.impl

import com.hyeok.recipebook.data.repository.IngredientRepository
import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import kotlinx.coroutines.flow.Flow

class IngredientRepositoryImpl: IngredientRepository {
    override fun getIngredients(): Flow<List<IngredientUiModel>> {
        TODO("Not yet implemented")
    }
}