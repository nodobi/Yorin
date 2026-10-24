package com.hyeok.recipebook.presentation.recipe.edit.state

import androidx.compose.runtime.Stable
import com.hyeok.recipebook.presentation.recipe.detail.records.RecipeRecordUiModel
import com.hyeok.recipebook.presentation.recipe.detail.step.RecipeStepUiModel
import com.hyeok.recipebook.presentation.recipe.edit.model.RecipeIngredientEditUiModel


@Stable
sealed interface RecipeEditUiState {
    data object Loading : RecipeEditUiState

    data class Success(
        val name: String = "",
        val cookingTime: Int? = null,
        val photoUrl: String? = null,
        val ingredients: List<RecipeIngredientEditUiModel>,
        val steps: List<RecipeStepUiModel>,
        val cookingRecords: List<RecipeRecordUiModel>
    ) : RecipeEditUiState
}