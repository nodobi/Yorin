package com.hyeok.recipebook.presentation.ingredient.list

import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel

data class IngredientsUiState(
    val ingredients: List<IngredientUiModel>,
    val expiredCount: Int,
    val remainExpirationDays: List<Int>,
) {
    companion object {
        fun fake() = IngredientsUiState(
            ingredients = listOf(IngredientUiModel.fake()),
            expiredCount = 1,
            remainExpirationDays = listOf(1)
        )

        fun empty() = IngredientsUiState(
            ingredients = emptyList(),
            expiredCount = 0,
            remainExpirationDays = emptyList()
        )
    }
}