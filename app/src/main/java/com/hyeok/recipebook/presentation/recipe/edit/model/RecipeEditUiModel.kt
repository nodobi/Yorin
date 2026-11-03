package com.hyeok.recipebook.presentation.recipe.edit.model

import androidx.compose.runtime.Stable
import com.hyeok.recipebook.presentation.recipe.detail.records.RecipeRecordUiModel
import com.hyeok.recipebook.presentation.recipe.detail.step.RecipeStepUiModel

@Stable
data class RecipeEditUiModel(
    val name: String,
    val cookingTime: Int,
    val photoUrl: String? = null,
    val ingredients: List<RecipeIngredientEditUiModel>,
    val steps: List<RecipeStepUiModel>,
    val cookingRecords: List<RecipeRecordUiModel>,
) {
    companion object {
        fun empty() = RecipeEditUiModel(
            name = "",
            cookingTime = 0,
            ingredients = emptyList(),
            steps = emptyList(),
            cookingRecords = emptyList()
        )
    }
}