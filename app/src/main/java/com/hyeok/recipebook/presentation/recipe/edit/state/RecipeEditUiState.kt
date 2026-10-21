package com.hyeok.recipebook.presentation.recipe.edit.state

import androidx.compose.runtime.Stable
import com.hyeok.recipebook.presentation.recipe.model.RecipeUiModel


@Stable
sealed interface RecipeEditUiState {
    data object Loading : RecipeEditUiState

    data class Success(
        val recipe: RecipeUiModel,
        val showRecordActionSheet: Boolean = false,
        val showRecordEditSheet: Boolean = false
    ) : RecipeEditUiState
}